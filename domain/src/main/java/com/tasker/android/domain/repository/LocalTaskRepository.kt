package com.tasker.android.domain.repository

import com.tasker.android.common.model.room.LocalTask
import kotlinx.coroutines.flow.Flow

interface LocalTaskRepository {

    fun getTaskByDate(year: Int, month: Int, day: Int): Flow<List<LocalTask>>
    suspend fun insert(task: LocalTask)
}