package com.tasker.android.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeTaskData(
    val text: String,
    val isCompleted: Boolean = false,
    val isFilled: Boolean = false,
    val categoryTag: String = "",
    val timeTag: String = "",
) : Parcelable