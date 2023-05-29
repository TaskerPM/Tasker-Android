package com.tasker.android.home.presentation.model

data class HomeListViewTaskData(
    val text: String,
    val isCompleted: Boolean,
    val isFilled: Boolean,
    val categoryTag: String,
    val timeTag: String,
)