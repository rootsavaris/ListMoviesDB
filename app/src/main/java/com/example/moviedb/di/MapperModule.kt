package com.example.moviedb.di

import com.example.moviedb.domain.mapper.MovieMapper
import com.example.moviedb.domain.mapper.MovieMapperImpl
import org.koin.dsl.module

val mapperModule = module {
    single<MovieMapper> { MovieMapperImpl() }
}