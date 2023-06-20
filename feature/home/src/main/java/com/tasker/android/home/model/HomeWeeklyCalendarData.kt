package com.tasker.android.home.model

class HomeWeeklyCalendarData(
    val year: Int,
    val month: Int,
    val day: Int,
    var isSelected: Boolean,
) {
    override fun toString(): String {
        return "HomeWeeklyCalendarData(year=$year, month=$month, day=$day, isSelected=$isSelected)"
    }
}