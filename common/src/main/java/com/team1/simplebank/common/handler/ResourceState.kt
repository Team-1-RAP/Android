package com.team1.simplebank.common.handler

sealed class ResourceState<out R> private constructor() {
    data object Loading: ResourceState<Nothing>()
    data class Success<out T>(val data: T): ResourceState<T>()
    data class Error(val exception: String?): ResourceState<Nothing>()
}