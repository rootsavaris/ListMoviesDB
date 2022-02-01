package com.example.moviedb.domain.mapper

import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.MovieResponse

interface MovieMapper {
    fun mapRemoteMovieToDomain(movieResponse: MovieResponse) : Movie
}