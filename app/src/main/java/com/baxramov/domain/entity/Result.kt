package com.baxramov.domain.entity

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class InternetError(val message: String) : Result<Nothing>()

    data class IncorrectCityNameError(val message: String) : Result<Nothing>()
}
