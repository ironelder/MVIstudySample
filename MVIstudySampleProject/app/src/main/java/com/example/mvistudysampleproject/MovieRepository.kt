package com.example.mvistudysampleproject

import retrofit2.Response

interface MovieRepository {
    suspend fun getNowPlayingMovieList(page: Int = 1): Response<Any>
}