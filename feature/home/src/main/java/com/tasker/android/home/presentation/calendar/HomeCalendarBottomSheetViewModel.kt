package com.tasker.android.home.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeCalendarBottomSheetViewModel : ViewModel() {

    private val _selectedYear = MutableStateFlow(0)
    val selectedYear: StateFlow<Int> get() = _selectedYear

    private val _selectedMonth = MutableStateFlow(0)
    val selectedMonth: StateFlow<Int> get() = _selectedMonth

    private val _selectedDay = MutableStateFlow(0)
    val selectedDay: StateFlow<Int> get() = _selectedDay

    fun initDate(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            _selectedYear.emit(year)
            _selectedMonth.emit(month)
            _selectedDay.emit(day)
        }
    }

    fun selectMonthForward() {
        if (selectedMonth.value == 12) {
            _selectedYear.value += 1
            _selectedMonth.value = 1
        } else {
            _selectedMonth.value += 1
        }
    }

    fun selectMonthBack() {
        if (_selectedMonth.value == 1) {
            _selectedYear.value -= 1
            _selectedMonth.value = 12
        } else {
            _selectedMonth.value -= 1
        }
    }

    fun selectDay(day: Int) {
        viewModelScope.launch {
            _selectedDay.emit(day)
        }
    }

    fun selectToday() {
        viewModelScope.launch {
            val today = LocalDate.now()

            _selectedYear.emit(today.year)
            _selectedMonth.emit(today.monthValue)
            _selectedDay.emit(today.dayOfMonth)
        }

    }
}