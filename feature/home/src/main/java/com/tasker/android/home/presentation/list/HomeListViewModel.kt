package com.tasker.android.home.presentation.list

import androidx.lifecycle.ViewModel
import com.tasker.android.home.model.HomeTaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeListViewModel : ViewModel() {

    private val _taskList = MutableStateFlow(mutableListOf<HomeTaskData>())
    val taskList: StateFlow<List<HomeTaskData>> get() = _taskList

    fun setTaskListDummyData() {
        val list = mutableListOf<HomeTaskData>()

        list.add(HomeTaskData("IA 구조도 그리기", false, true, "스터디", "14:00-17:00"))
        list.add(HomeTaskData("와이어프레임 제작-lofi", false, false, "", ""))
        list.add(HomeTaskData("IA 구조도 그리기", true, true, "스터디", "14:00-17:00"))
        list.add(HomeTaskData("와이어프레임 제작-lofi", true, false, "", ""))

        _taskList.value = list
    }
}