package com.tasker.android.common.model.err

data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val timeStamp: String,
)
