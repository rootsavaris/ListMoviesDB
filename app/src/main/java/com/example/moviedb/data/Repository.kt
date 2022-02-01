package com.example.moviedb.data

import androidx.paging.PagingData
import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.ApiResponse
import com.example.moviedb.framework.network.model.MovieResponse
import kotlinx.coroutines.flow.*

class Repository constructor(private val iLocalDataSource: ILocalDataSource, private val iRemoteDataSource: IRemoteDataSource) :
    IRepository {

    override fun getListMovies(): Flow<PagingData<Movie>> {
        return iRemoteDataSource.getListMovies()
    }

    override suspend fun getMovie(movieId: Int): Flow<ApiResponse<Movie>> {
        return flow {
            emit( iRemoteDataSource.getMovie(movieId))
        }
    }
}