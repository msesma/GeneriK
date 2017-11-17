package com.paradigmadigital.repository

fun <T> List<T>.toApiResult() = ApiResult.Success(this)
fun  Throwable.toApiResult() = ApiResult.Failure(this.hashCode().toString())

sealed class ApiResult {
    data class Success<out T>(val data: T) : ApiResult()
    data class Failure(val data: String) : ApiResult()
}