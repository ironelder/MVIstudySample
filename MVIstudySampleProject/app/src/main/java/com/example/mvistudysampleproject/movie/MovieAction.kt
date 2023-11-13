package com.example.mvistudysampleproject.movie

sealed class MovieAction {
    data class LoadMovie(val page: Int = 1, val type: FilterType = FilterType.All()) : MovieAction()
    data class RefreshMovie(val type: FilterType = FilterType.All()) : MovieAction()
    object AnyAction : MovieAction()
}
