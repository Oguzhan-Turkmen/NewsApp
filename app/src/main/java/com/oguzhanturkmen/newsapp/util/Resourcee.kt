package com.oguzhanturkmen.newsapp.util

sealed class Resourcee<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resourcee<T>(data)
    class Error<T>(message: String, data: T? = null) : Resourcee<T>(data, message)
    class Loading<T> : Resourcee<T>()
}