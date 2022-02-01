package com.example.moviedb.domain.mapper

import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.MovieResponse

class MovieMapperImpl : MovieMapper {
    override fun mapRemoteMovieToDomain(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id ?: 0,
            originalTitle = movieResponse.originalTitle ?: "",
            posterPath = movieResponse.posterPath ?: "",
            overview = movieResponse.overview ?: ""
        )
    }
}