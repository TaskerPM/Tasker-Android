package com.tasker.android.common.model.server.err

data class ErrorResponse(
    val errorCode: String,
    val message: String?,
    val timeStamp: List<Int>?,
)
