package com.example.mvistudysampleproject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun getNowPlayingMovieList(page: Int = 1): Flow<*> = flow<Any> {
        val result = repository.getNowPlayingMovieList(page)
        emit(result)
    }
}