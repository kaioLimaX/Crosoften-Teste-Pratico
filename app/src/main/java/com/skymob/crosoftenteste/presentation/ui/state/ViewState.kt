package com.skymob.crosoftenteste.presentation.ui.state

sealed class ViewState<T> (
    open val data: T? = null,
    open val message: String? = null

){
    class Sucess<T>(data: T) : ViewState<T>(data)
    class Error<T>(message: String, data: T? = null) : ViewState<T>(data, message)
    class Loading<T> : ViewState<T>()

}