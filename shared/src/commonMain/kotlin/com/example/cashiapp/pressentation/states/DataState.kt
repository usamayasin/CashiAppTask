package com.example.cashiapp.pressentation.states

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : DataState<Nothing>()
}