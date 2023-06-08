package com.tasker.android.home.presentation.model

data class HomeListViewTaskData(
    val text: String,
    val isCompleted: Boolean = false,
    val isFilled: Boolean = false,
    val categoryTag: String = "",
    val timeTag: String = "",
)