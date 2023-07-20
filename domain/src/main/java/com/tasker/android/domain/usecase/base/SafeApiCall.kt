package com.tasker.android.domain.usecase.base

import com.google.gson.Gson
import com.tasker.android.common.model.server.err.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>,
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    ResultWrapper.Success(response.body()!!)
                } else {
                    ResultWrapper.GenericError(ErrorResponse("empty response", "empty response", emptyList()))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                ResultWrapper.GenericError(errorResponse)
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    ResultWrapper.NetworkError
                }
                is HttpException -> {
                    ResultWrapper.Exception(t)
                }
                else -> {
                    ResultWrapper.Exception(t)
                }
            }
        }
    }
}