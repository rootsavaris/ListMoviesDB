package com.example.moviedb.framework.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedb.framework.network.model.MovieResponse
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val service: MovieServiceApi
): PagingSource<Int, MovieResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            delay(1000)
            val response = service.getMovies(page = pageNumber)
            val movies = response.results
            val nextKey =
                if (movies?.isEmpty() == true) {
                    null
                } else {
                    pageNumber + 1
                }
            LoadResult.Page(
                data = movies.orEmpty(),
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}