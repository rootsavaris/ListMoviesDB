package com.example.moviedb.presentation.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.moviedb.base.BaseViewModel
import com.example.moviedb.framework.network.model.ApiResponse
import com.example.moviedb.interactors.GetMovieUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    application: Application,
    private val getMovieUseCase: GetMovieUseCase
) : BaseViewModel(application) {

    private val _viewState = MutableStateFlow<ViewState>(ViewState())
    val viewState = _viewState.asStateFlow()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun getMovie(movieId: Int) = viewModelScope.launch {
        getMovieUseCase.invoke(movieId).collect{ result ->
            if (result is ApiResponse.Success){
                _viewState.value = _viewState.value.copy(isLoading = false, success = true, successValue = result.data)
            } else if (result is ApiResponse.Failure){
                _viewState.value = _viewState.value.copy(isLoading = false, error = true, errorMessage = result.e.toString())
            }
        }
    }

    fun tryAgain(movieId: Int){
        _viewState.value = _viewState.value.copy(isLoading = true, success = false, error = false)
        getMovie(movieId)
    }

    fun showSnackBar(text: String) {
        viewModelScope.launch {
            eventChannel.send(Event.ShowSnackBar(text))
        }
    }

    fun backPress() {
        viewModelScope.launch {
            eventChannel.send(Event.NavigationBack)
        }
    }

    sealed class Event {
        data class ShowSnackBar(val text: String): Event()
        object NavigationBack: Event()
    }

}