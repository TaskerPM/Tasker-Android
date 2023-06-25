package com.tasker.android.home.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.home.model.HomeTaskData
import com.tasker.android.home.model.HomeWeeklyCalendarData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeListViewModel : ViewModel() {

    private val _taskList = MutableStateFlow<List<HomeTaskData>>(emptyList())
    val taskList: StateFlow<List<HomeTaskData>> get() = _taskList

    fun initTaskList(selectedDate: HomeWeeklyCalendarData, list: List<HomeTaskData>) {
        viewModelScope.launch {
            val filteredList = list.filter { task ->
                task.year == selectedDate.year && task.month == selectedDate.month && task.day == selectedDate.day
            }

            _taskList.emit(filteredList)
        }
    }

    fun addTask(text: String) {
        viewModelScope.launch {
            val list = _taskList.value.toMutableList()
            list.add(HomeTaskData(text))

            _taskList.emit(list)
        }
    }

    fun removeTask(position: Int) {
        viewModelScope.launch {
            val list = _taskList.value.toMutableList()
            list.removeAt(position)

            _taskList.emit(list)
        }
    }
}