package com.tasker.android.home.presentation.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.YearMonth

class HomeCalendarBottomSheetViewModel : ViewModel() {

    val selectedYear = MutableStateFlow(YearMonth.now().year)
    val selectedMonth = MutableStateFlow(YearMonth.now().monthValue)

    fun selectForward() {
        if (selectedMonth.value == 12) {
            selectedYear.value += 1
            selectedMonth.value = 1
        } else {
            selectedMonth.value += 1
        }
    }

    fun selectBack() {
        if (selectedMonth.value == 1) {
            selectedYear.value -= 1
            selectedMonth.value = 12
        } else {
            selectedMonth.value -= 1
        }
    }

    fun selectToday() {
        selectedYear.value = YearMonth.now().year
        selectedMonth.value = YearMonth.now().monthValue
    }
}