package com.example.moviedb.data

import androidx.paging.PagingData
import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.ApiResponse
import com.example.moviedb.framework.network.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getListMovies(): Flow<PagingData<Movie>>
    suspend fun getMovie(movieId: Int): Flow<ApiResponse<Movie>>
}