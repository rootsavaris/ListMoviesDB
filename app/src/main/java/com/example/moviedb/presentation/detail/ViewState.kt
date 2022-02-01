package com.example.moviedb.presentation.detail

import com.example.moviedb.domain.Movie

data class ViewState(
    val isLoading: Boolean = true,
    val success: Boolean = false,
    val successValue: Movie? = null,
    val error: Boolean = false,
    val errorMessage: String? = null
){
    /*
    fun loadingOn(): ViewState = this.copy(isLoading = true, stepOneEnable = false, clearFields = false)
    fun successStep1(localCurrency: String): ViewState = this.copy(isLoading = false, stepTwoEnable = true, localCurrency = localCurrency, editButtonEnable = true)
    fun failureStep1(): ViewState = this.copy(isLoading = false, stepOneEnable = true)
    fun enableToSend(): ViewState = this.copy(stepTwoEnable = false, btnSendEnable = true)
    fun loadInitialState(): ViewState = this.copy(isLoading = false, stepOneEnable = true, stepTwoEnable = false, btnSendEnable = false, localCurrency = null, clearFields = true, editButtonEnable = false)
    */
}
