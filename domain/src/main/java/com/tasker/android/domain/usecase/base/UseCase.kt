package com.tasker.android.domain.usecase.base

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


abstract class UseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(): ResultWrapper<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute().let {
                    ResultWrapper.Success(it)
                }
            }
        } catch (t: Throwable) {
            Log.d("throwable", t.toString())
            ResultWrapper.GenericError(null)
        }
    }

    protected abstract suspend fun execute(): R
}