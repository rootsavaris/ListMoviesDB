package com.example.moviedb.framework.network

import com.example.moviedb.framework.network.model.MovieListResponse
import com.example.moviedb.framework.network.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServiceApi {

    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query: String = "beat",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int
    ): MovieResponse

}