package com.paradigmadigital.repository

import retrofit2.HttpException

fun <T> T.toApiResult() = ApiResult.Success(this)
fun <T> List<T>.toApiResult() = ApiResult.Success(this)
fun Throwable.toApiResult() = when (this) {
    is HttpException -> ApiResult.Failure(this.code().toString())
    else -> ApiResult.Failure(this.toString())
}

sealed class ApiResult {
    data class Success<out T>(val data: T) : ApiResult()
    data class Failure(val data: String) : ApiResult()
}