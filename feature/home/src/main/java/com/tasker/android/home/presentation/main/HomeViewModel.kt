package com.tasker.android.home.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.home.presentation.model.HomeWeeklyCalendarData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.WeekFields

class HomeViewModel : ViewModel() {

    private val _selectedDate = MutableStateFlow(
        HomeWeeklyCalendarData(
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth,
            true
        )
    )

    val selectedDate: StateFlow<HomeWeeklyCalendarData> get() = _selectedDate

    private val _datePickerList = MutableStateFlow<List<HomeWeeklyCalendarData>>(emptyList())
    val datePickerList: StateFlow<List<HomeWeeklyCalendarData>> = _datePickerList

    fun selectDate(day: HomeWeeklyCalendarData) {
        viewModelScope.launch {
            _selectedDate.emit(HomeWeeklyCalendarData(day.year, day.month, day.day, true))

            // get tasks of selectedDate here

        }
    }

    fun getDatePickerList() {
        viewModelScope.launch {
            val sd = _selectedDate.value
            val weekFields: WeekFields = WeekFields.ISO

            val selectedDayOfWeek = LocalDate.of(sd.year, sd.month, sd.day).dayOfWeek.value

            val list = mutableListOf<HomeWeeklyCalendarData>()
            for (i in 1..7) {
                val day = LocalDate
                    .of(sd.year, sd.month, sd.day)
                    .with(weekFields.dayOfWeek(), i.toLong())

                list.add(
                    HomeWeeklyCalendarData(
                        day.year,
                        day.monthValue,
                        day.dayOfMonth,
                        i == selectedDayOfWeek
                    )
                )
            }

            _datePickerList.emit(list)
        }

    }
}