package com.paradigmadigital.repository


sealed class DataResult {
    data class Success<T>(val data: T) : DataResult()
    data class Failure(val data: String) : DataResult()
}