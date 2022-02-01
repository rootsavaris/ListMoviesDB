package com.example.moviedb.presentation.list

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.data.IRepository
import com.example.moviedb.domain.Movie
import com.example.moviedb.framework.network.model.MovieResponse
import com.example.moviedb.interactors.GetMoviesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListMoviesViewModel(
    application: Application,
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel(application) {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun getMovies(): Flow<PagingData<Movie>> {
        return getMoviesUseCase.invoke().cachedIn(viewModelScope)
    }

    fun navigateToDetail(movieId: Int) {
        viewModelScope.launch {
            eventChannel.send(Event.NavigateToDetail(movieId))
        }
    }

    fun showSnackBar(text: String) {
        viewModelScope.launch {
            eventChannel.send(Event.ShowSnackBar(text))
        }
    }

    sealed class Event {
        data class NavigateToDetail(val movieId: Int): Event()
        data class ShowSnackBar(val text: String): Event()
    }

}