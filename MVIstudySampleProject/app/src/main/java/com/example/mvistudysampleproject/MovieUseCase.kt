package com.example.mvistudysampleproject

import com.example.mvistudysampleproject.entity.MovieListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getNowPlayingMovieList(page: Int = 1): Flow<MovieListResponse> = flow<MovieListResponse> {
        val result = repository.getNowPlayingMovieList(page)
        emit(result)
    }
}