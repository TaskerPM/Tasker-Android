package com.tasker.android.home.presentation.list

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.common.model.server.task.req.PostTaskReq
import com.tasker.android.common.util.getSharedPrefAccessToken
import com.tasker.android.domain.usecase.base.ResultWrapper
import com.tasker.android.domain.usecase.local.GetLocalTaskUseCase
import com.tasker.android.domain.usecase.local.InsertLocalTaskUseCase
import com.tasker.android.domain.usecase.task.GetTaskByDateUseCase
import com.tasker.android.domain.usecase.task.PostTaskUseCase
import com.tasker.android.home.model.HomeWeeklyCalendarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val getLocalTaskUseCase: GetLocalTaskUseCase,
    private val getTaskByDateUseCase: GetTaskByDateUseCase,
    private val insertLocalTaskUseCase: InsertLocalTaskUseCase,
    private val postTaskUseCase: PostTaskUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _taskList = MutableStateFlow<List<LocalTask>>(emptyList())
    val taskList: StateFlow<List<LocalTask>> get() = _taskList

    fun initTaskList(selectedDate: HomeWeeklyCalendarData) {
        viewModelScope.launch {
//            val filteredList = list.filter { task ->
//                task.year == selectedDate.year && task.month == selectedDate.month && task.day == selectedDate.day
//            }
//
//            _taskList.emit(filteredList)

            getLocalTaskUseCase(selectedDate.year, selectedDate.month, selectedDate.day)
                .collect {
                    _taskList.emit(it)
                }
        }
    }

    fun addTask(text: String, selectedDate: HomeWeeklyCalendarData) {
        viewModelScope.launch {
            insertLocalTaskUseCase(
                LocalTask(
                    title = text,
                    year = selectedDate.year,
                    month = selectedDate.month,
                    day = selectedDate.day,
                )
            )

            val date = formatDateToString(selectedDate.year, selectedDate.month, selectedDate.day)
            postTaskUseCase(
                accessToken = getSharedPrefAccessToken(sharedPreferences),
                date = date,
                data = PostTaskReq(title = text)
            ).let {
                when (it) {
                    is ResultWrapper.Success -> Log.d("response success", it.toString())
                    is ResultWrapper.Exception -> Log.d("response exception", it.toString())
                    is ResultWrapper.GenericError -> Log.d("response generic", it.toString())
                    ResultWrapper.NetworkError -> Log.d("response network", it.toString())
                }
            }
        }
    }

    fun removeTask(position: Int) {
        viewModelScope.launch {
//            val list = _taskList.value.toMutableList()
//            list.removeAt(position)
//
//            _taskList.emit(list)
        }
    }

    private fun formatDateToString(year: Int, month: Int, day: Int): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MMdd")
        val date = LocalDate.of(year, month, day)
        return date.format(formatter)
    }
}