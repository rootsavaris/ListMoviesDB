package com.example.moviedb.di

import com.example.moviedb.interactors.GetMovieUseCase
import com.example.moviedb.interactors.GetMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetMoviesUseCase(get())
    }
    factory {
        GetMovieUseCase(get())
    }

}
