package com.example.mvistudysampleproject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovieList(@Query("language") language: String = "en-US", @Query("page") page: Int = 1): Response<Any>
}