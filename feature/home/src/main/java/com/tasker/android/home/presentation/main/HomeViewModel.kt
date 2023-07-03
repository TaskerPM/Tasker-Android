package com.tasker.android.home.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.home.model.HomeTaskData
import com.tasker.android.home.model.HomeWeeklyCalendarData
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

    private val _taskList = MutableStateFlow<List<HomeTaskData>>(mutableListOf())
    val taskList: StateFlow<List<HomeTaskData>> = _taskList

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

    fun setTaskListDummyData() {
        viewModelScope.launch {
            val list = mutableListOf<HomeTaskData>()
            val today = LocalDate.now()

            list.add(
                HomeTaskData(
                    "IA 구조도 그리기",
                    false,
                    true,
                    "스터디",
                    "14:00-17:00",
                    today.year,
                    today.monthValue,
                    today.dayOfMonth
                )
            )
            list.add(
                HomeTaskData(
                    "와이어프레임 제작-lofi",
                    false,
                    false,
                    "",
                    "",
                    today.year,
                    today.monthValue,
                    today.dayOfMonth
                )
            )
            list.add(
                HomeTaskData(
                    "IA 구조도 그리기",
                    true,
                    true,
                    "스터디",
                    "14:00-17:00",
                    today.year,
                    today.monthValue,
                    today.dayOfMonth
                )
            )
            list.add(
                HomeTaskData(
                    "와이어프레임 제작-lofi",
                    true,
                    false,
                    "",
                    "",
                    today.year,
                    today.monthValue,
                    today.dayOfMonth
                )
            )

            _taskList.emit(list)
        }
    }

    fun addTask(text: String) {
        viewModelScope.launch {
            val selectedDate = _selectedDate.value
            val list = _taskList.value.toMutableList()
            list.add(
                HomeTaskData(
                    text = text,
                    year = selectedDate.year,
                    month = selectedDate.month,
                    day = selectedDate.day
                )
            )

            _taskList.emit(list)
        }
    }

    fun removeTask(task: HomeTaskData) {
        viewModelScope.launch {
            val updatedList = _taskList.value.toMutableList()
            updatedList.remove(task)
            _taskList.emit(updatedList)
        }
    }
}