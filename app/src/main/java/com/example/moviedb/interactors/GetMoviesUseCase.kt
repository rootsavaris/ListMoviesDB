package com.example.moviedb.interactors

import androidx.paging.PagingData
import com.example.moviedb.data.IRepository
import com.example.moviedb.domain.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(private val iRepository: IRepository)  {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return iRepository.getListMovies()
    }
}