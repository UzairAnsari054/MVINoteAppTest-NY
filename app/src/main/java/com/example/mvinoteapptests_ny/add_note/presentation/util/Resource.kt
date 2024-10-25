package com.example.mvinoteapptests_ny.add_note.presentation.util

sealed class Resource<T>(
    val data: T? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(val isLoading: Boolean = false) : Resource<T>(null)
}