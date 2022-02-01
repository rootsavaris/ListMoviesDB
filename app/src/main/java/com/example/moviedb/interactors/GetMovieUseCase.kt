package com.example.moviedb.interactors

import com.example.moviedb.data.IRepository
import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetMovieUseCase(private val iRepository: IRepository,
                      private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun invoke(movieId: Int): Flow<ApiResponse<Movie>> {
        return iRepository.getMovie(movieId).flowOn(coroutineDispatcher)
    }

}