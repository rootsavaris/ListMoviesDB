package com.example.moviedb.di

import com.example.moviedb.data.ILocalDataSource
import com.example.moviedb.data.IRemoteDataSource
import com.example.moviedb.data.IRepository
import com.example.moviedb.data.Repository
import com.example.moviedb.domain.mapper.MovieMapper
import com.example.moviedb.framework.database.LocalDataSource
import com.example.moviedb.framework.network.MovieServiceApi
import com.example.moviedb.framework.network.RemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {
    factory { provideLocalDataSource() }
    factory { provideRemoteDataSource(get(), get()) }
    single { provideRepository(get(), get()) }
}

fun provideRepository(iLocalDataSource: ILocalDataSource, iRemoteDataSource: IRemoteDataSource): IRepository {
    return Repository(iLocalDataSource, iRemoteDataSource)
}

fun provideLocalDataSource(): ILocalDataSource {
    return LocalDataSource()
}

fun provideRemoteDataSource(moviesServiceApi: MovieServiceApi, movieMapper: MovieMapper): IRemoteDataSource {
    return RemoteDataSource(moviesServiceApi, movieMapper)
}
