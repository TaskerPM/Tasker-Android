package com.tasker.android.home.presentation.model

data class HomeTaskData(
    val text: String,
    val isCompleted: Boolean = false,
    val isFilled: Boolean = false,
    val categoryTag: String = "",
    val timeTag: String = "",
)