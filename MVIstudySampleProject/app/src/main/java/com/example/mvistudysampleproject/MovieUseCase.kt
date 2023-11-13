package com.example.mvistudysampleproject

import com.example.mvistudysampleproject.entity.MovieListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//region : all , us , ca , fr , de
class MovieUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getNowPlayingMovieList(page: Int = 1): Flow<MovieListResponse> = flow<MovieListResponse> {
        val result = repository.getNowPlayingMovieList(page)
        emit(result)
    }.flowOn(Dispatchers.IO)
}
