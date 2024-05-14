package com.alparslanturk.bulbiletini.data.entities.models

sealed class Result<T> {
    data class Loading<T>(var isLoading: Boolean = true) : Result<T>()
    data class Success<T>(val body: T?, val code: Int, val message: String?) : Result<T>()
    data class Error<T>(val code: Int = 500, val message: String?) : Result<T>()
}