package com.example.mvistudysampleproject.movie

import com.example.mvistudysampleproject.base.MviState
import com.example.mvistudysampleproject.entity.Dates
import com.example.mvistudysampleproject.entity.Result

data class MovieState(
    val isLoading: Boolean,
    val filter: FilterType,
    val dates: Dates?,
    val nextPage: Int,
    val movieList: List<Result>,
    val totalPages: Int,
    val initial: Boolean,
    val error: Throwable?,
    val message: String?,
) : MviState {
    companion object {
        fun initialized(): MovieState {
            return MovieState(
                isLoading = false,
                filter = FilterType.All(),
                dates = null,
                nextPage = 0,
                movieList = emptyList(),
                totalPages = 0,
                initial = true,
                error = null,
                message = null,
            )
        }
    }
}
