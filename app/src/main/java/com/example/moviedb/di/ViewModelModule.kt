package com.example.moviedb.di

import com.example.moviedb.presentation.detail.DetailMovieViewModel
import com.example.moviedb.presentation.list.ListMoviesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ListMoviesViewModel(androidApplication(), get())
    }
    viewModel {
        DetailMovieViewModel(androidApplication(), get())
    }
}
