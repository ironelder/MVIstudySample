package com.example.mvistudysampleproject.movie

import com.example.mvistudysampleproject.base.MviResult
import com.example.mvistudysampleproject.entity.MovieListResponse

sealed interface MovieResult : MviResult {
    sealed class LoadMovieResult : MovieResult {
        data class Success(val filterType: FilterType, val movieResponse: MovieListResponse) : LoadMovieResult()
        data class Failure(val error: Throwable) : LoadMovieResult()
        data class InProgress(val isRefresh: Boolean) : LoadMovieResult()
    }

    sealed class RefreshMovieResult : MovieResult {
        data class Success(val filterType: FilterType, val movieResponse: MovieListResponse) : LoadMovieResult()
        data class Failure(val error: Throwable) : LoadMovieResult()
        data class InProgress(val isRefresh: Boolean) : LoadMovieResult()
    }
}
