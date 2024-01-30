package com.alparslanturk.kombineapp.data.entities.models

sealed class Result<T> {
    data class Loading<T>(val isLoading: Boolean = true) : Result<T>()
    data class Success<T>(val body: T?, val code: Int, val message: String?) : Result<T>()
    data class Error<T>(val code: Int = 500, val message: String?) : Result<T>()
    data class Auth<T>(val code: Int = 401, val message: String?) : Result<T>()
}