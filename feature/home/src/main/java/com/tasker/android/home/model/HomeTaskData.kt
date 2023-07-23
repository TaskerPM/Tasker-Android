package com.tasker.android.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeTaskData(
    var text: String = "",
    val isCompleted: Boolean = false,
    val isFilled: Boolean = false,
    val categoryTag: String = "",
    val timeTag: String = "",
    val year: Int = 0,
    val month: Int = 0,
    val day: Int = 0,
) : Parcelable