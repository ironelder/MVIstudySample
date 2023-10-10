package com.example.mvistudysampleproject

import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val service: MovieApiService) : MovieRepository {
    override suspend fun getNowPlayingMovieList(page: Int): Response<Any> {
        return service.getNowPlayingMovieList(page)
    }
}