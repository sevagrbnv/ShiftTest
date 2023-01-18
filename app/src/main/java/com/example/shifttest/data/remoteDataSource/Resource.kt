package com.example.shifttest.data.remoteDataSource

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null) {

    class Success<T>(data:T?): Resource<T>(data = data)
    class Loading<T>(isLoading: Boolean = true): Resource<T>(data = null)
    class Error<T>(data:T? = null, message: String?): Resource<T>(data = data, message = null)
}