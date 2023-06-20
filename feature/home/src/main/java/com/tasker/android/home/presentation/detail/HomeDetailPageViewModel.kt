package com.tasker.android.home.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.home.model.HomeTaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeDetailPageViewModel : ViewModel() {

    private val _selectedTask = MutableStateFlow(HomeTaskData(""))
    val selectedTaskData: StateFlow<HomeTaskData> get() = _selectedTask

    private val _noteList = MutableStateFlow<List<String>>(emptyList())
    val noteList: StateFlow<List<String>> get() = _noteList

    fun selectTask(task: HomeTaskData) {
        viewModelScope.launch {
            _selectedTask.emit(task)
        }
    }

    fun getDummyNotes() {
        viewModelScope.launch {
            val dummyList = mutableListOf<String>()

            for (i in 1..2) {
                dummyList.add("2000년 10월에 일본 소니에서 프로토타입을 발표한 뒤에 2003년 4월부터 시판되어 DVD의 뒤를 이은 고용량 광학식 저장 매체이다. 약자는 CD, DVD의 뒤를 이어 \"BD\". 그러나 '비디'라고 읽는 사람은 드물고 대부분 블루레이 (Blu-ray)라고 부른다. 이후 2015년에 후속 규격인 Ultra HD Blu-ray가 표준화되었다.")
            }

            _noteList.emit(dummyList)
        }
    }
}