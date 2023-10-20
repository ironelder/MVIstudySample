package com.example.mvistudysampleproject

import com.example.mvistudysampleproject.entity.MovieListResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getNowPlayingMovieList(page: Int = 1): MovieListResponse
}