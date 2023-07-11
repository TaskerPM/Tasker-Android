package com.tasker.android.common.model

data class ApplicationResponse<T>(
    val code: String,
    val data: T,
    val message: String,
    val timestamp: String,
)
