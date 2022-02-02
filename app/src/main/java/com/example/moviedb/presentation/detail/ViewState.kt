package com.example.moviedb.presentation.detail

import com.example.moviedb.domain.Movie

data class ViewState(
    val isLoading: Boolean = true,
    val success: Boolean = false,
    val successValue: Movie? = null,
    val error: Boolean = false,
    val errorMessage: String? = null
){
    fun loadInitial(): ViewState = this.copy(isLoading = true, success = false, error = false)
    fun loadSuccess(movie: Movie): ViewState = this.copy(isLoading = false, success = true, successValue = movie)
    fun loadFailure(message: String): ViewState = this.copy(isLoading = false, error = true, errorMessage = message)
}
