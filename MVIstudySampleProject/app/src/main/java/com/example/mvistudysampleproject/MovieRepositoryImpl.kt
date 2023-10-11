package com.example.mvistudysampleproject

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MovieRepositoryImpl @Inject constructor(private val service: MovieApiService) : MovieRepository {
    override suspend fun getNowPlayingMovieList(page: Int): Response<Any> {
        return service.getNowPlayingMovieList(page)
    }
}