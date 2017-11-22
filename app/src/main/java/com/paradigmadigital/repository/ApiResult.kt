package com.paradigmadigital.repository

import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

fun <T> T.toApiResult() = ApiResult.Success(this)
fun <T> List<T>.toApiResult() = ApiResult.Success(this)
fun Throwable.toApiResult() = ApiResult.Failure(manageExceptions(this))

private fun manageExceptions(e: Throwable): NetworkResultCode {
    return when {
        e is UnknownHostException -> NetworkResultCode.DISCONNECTED
        e is HttpException && e.code() == HttpURLConnection.HTTP_NOT_FOUND -> NetworkResultCode.BAD_URL
        e is HttpException && e.code() == HttpURLConnection.HTTP_FORBIDDEN -> NetworkResultCode.FORBIDDEN
        else -> NetworkResultCode.UNKNOWN
    }
}

sealed class ApiResult {
    data class Success<out T>(val data: T) : ApiResult()
    data class Failure(val data: NetworkResultCode) : ApiResult()
}