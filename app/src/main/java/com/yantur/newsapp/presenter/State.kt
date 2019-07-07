package com.yantur.newsapp.presenter

sealed class State<out R> {
    data class Complete<out T>(val data: T) : State<T>()
    data class Error(val exception: Exception) : State<Nothing>()
    object Loading : State<Nothing>()
}

open class Event<out T>(private val content: T) {

    private var hasBeenHandled: Boolean = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

typealias Result<T> = (T) -> Unit