package com.tasker.android.common.model.server.task.res

data class PostTaskRes(
    val date: String,
    val status: Int,
    val taskId : Long,
    val title: String,
)
