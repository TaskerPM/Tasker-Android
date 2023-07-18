package com.tasker.android.common.model.server.task.res

data class GetTaskRes(
    val categoryColorBack: String,
    val categoryColorText: String,
    val categoryName: String,
    val isCompleted: Int,
    val taskId: Long,
    val timeEnd: String,
    val timeStart: String,
    val title: String
)
