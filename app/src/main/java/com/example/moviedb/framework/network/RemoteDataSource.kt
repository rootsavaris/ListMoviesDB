package com.example.moviedb.framework.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviedb.data.IRemoteDataSource
import com.example.moviedb.domain.Movie
import com.example.moviedb.domain.mapper.MovieMapper
import com.example.moviedb.framework.network.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val NETWORK_PAGE_SIZE = 20

class RemoteDataSource(private val moviesServiceApi: MovieServiceApi, private val movieMapper: MovieMapper):
    IRemoteDataSource {

    override fun getListMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(moviesServiceApi) }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse -> movieMapper.mapRemoteMovieToDomain(movieResponse)}
        }
    }

    override suspend fun getMovie(movieId: Int): ApiResponse<Movie> {
        return try{
            ApiResponse.success(movieMapper.mapRemoteMovieToDomain(moviesServiceApi.getMovie(movieId = movieId)))
        } catch (exception: Exception) {
            ApiResponse.failure(exception)
        }
    }

}