package com.mat.shoppinglist

import retrofit2.HttpException

sealed class Result<T> {
    data class Success<T>(var data: T) : Result<T>()
    data class Error<T>(val exception: HttpException) : Result<T>()
}