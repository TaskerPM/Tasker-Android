package com.tasker.android.domain.usecase.base

import com.tasker.android.common.model.server.err.ErrorResponse

sealed class ResultWrapper<out T> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class GenericError(val error: ErrorResponse?) : ResultWrapper<Nothing>()
    data class Exception(val t: Throwable) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is GenericError -> "Error[exception=$error]"
            is Exception -> "Error[throwable=$t]"
            NetworkError -> "Loading"
        }
    }
}