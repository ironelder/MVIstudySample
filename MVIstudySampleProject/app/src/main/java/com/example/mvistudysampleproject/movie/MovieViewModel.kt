package com.example.mvistudysampleproject.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mvistudysampleproject.MovieUseCase
import com.example.mvistudysampleproject.base.BaseViewModel
import com.example.mvistudysampleproject.defer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class MovieViewModel @Inject constructor() : BaseViewModel<MovieIntent, MovieState>() {

    @Inject
    lateinit var useCase: MovieUseCase

    override val viewState: StateFlow<MovieState>

    private val reducer: (MovieState, MovieResult) -> MovieState = { beforeState, result ->
        when (result) {
            is MovieResult.LoadMovieResult.Success -> {
                Log.d("ironelder", "LoadMovieResult.Success ")
                beforeState.copy(
                    isLoading = false,
                    filter = result.filterType,
                    dates = result.movieResponse.dates,
                    nextPage = result.movieResponse.page + 1,
                    movieList = result.movieResponse.results,
                    totalPages = result.movieResponse.total_pages,
                    initial = false,
                    error = null,
                    message = null,
                )
            }

            is MovieResult.LoadMovieResult.InProgress -> {
                beforeState.copy(isLoading = true)
            }

            is MovieResult.RefreshMovieResult.Success -> {
                Log.d("ironelder", "RefreshMovieResult.Success ")
                beforeState.copy(
                    isLoading = false,
                    filter = result.filterType,
                    dates = result.movieResponse.dates,
                    nextPage = result.movieResponse.page,
                    movieList = result.movieResponse.results,
                    totalPages = result.movieResponse.total_pages,
                    initial = false,
                    error = null,
                    message = null,
                )
            }

            else -> {
                beforeState.copy()
            }
        }
    }

    init {
        val initialMovieState = MovieState.initialized()
        viewState = merge(
            intentFlow.filterIsInstance<MovieIntent.InitialIntent>().take(1),
            intentFlow.filterNot { it is MovieIntent.InitialIntent },
        )
            .map(this::actionFromIntent)
            .actionProcessor()
            .scan(initialMovieState) { state, result -> reducer(state, result) }
            .catch {
                Log.d("ironelder", "Error catch = ${it.message}")
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialMovieState)
    }

    private fun actionFromIntent(i: MovieIntent): MovieAction = when (i) {
        is MovieIntent.InitialIntent -> MovieAction.LoadMovie(page = 1, FilterType.All())
        is MovieIntent.FilterIntent -> {
            MovieAction.LoadMovie(page = 1, type = i.type)
        }

        is MovieIntent.SwipeToRefresh -> {
            Log.d("ironelder", "SwipeToRefresh")
            MovieAction.RefreshMovie(FilterType.All())
        }

        else -> MovieAction.AnyAction
    }

    private fun Flow<MovieAction>.actionProcessor(): Flow<MovieResult> {
        return merge(
            filterIsInstance<MovieAction.LoadMovie>().flatMapConcat { action ->
                val type = action.type.typeName
                Log.d("ironelder", "LoadMovie")
                defer { useCase.getNowPlayingMovieList(page = action.page) }
                    .map {
                        MovieResult.LoadMovieResult.Success(FilterType.All(), it)
                    }
                    .onStart { MovieResult.LoadMovieResult.InProgress(true) }
                    .catch { e -> MovieResult.LoadMovieResult.Failure(e.fillInStackTrace()) }
            },
            filterIsInstance<MovieAction.RefreshMovie>().flatMapConcat { action ->
                val type = action.type.typeName
                Log.d("ironelder", "RefreshMovie")
                defer { useCase.getNowPlayingMovieList(page = 1) }
                    .map {
                        MovieResult.RefreshMovieResult.Success(FilterType.All(), it)
                    }
                    .onStart { MovieResult.RefreshMovieResult.InProgress(true) }
                    .catch { e -> MovieResult.RefreshMovieResult.Failure(e.fillInStackTrace()) }
            },
        )
    }
}
