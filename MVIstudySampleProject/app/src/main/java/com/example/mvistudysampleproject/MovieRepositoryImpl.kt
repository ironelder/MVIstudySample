package com.example.mvistudysampleproject

import com.example.mvistudysampleproject.entity.MovieListResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class MovieRepositoryImpl @Inject constructor(private val service: MovieApiService) : MovieRepository {
    override suspend fun getNowPlayingMovieList(page: Int): MovieListResponse {
        return service.getNowPlayingMovieList("en-US", page)
    }
}