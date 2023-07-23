package com.tasker.android.data.repository

import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.data.room.LocalTaskDao
import com.tasker.android.domain.repository.LocalTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTaskRepositoryImpl @Inject constructor(
    private val localTaskDao: LocalTaskDao,
) : LocalTaskRepository {

    override fun getTaskByDate(year: Int, month: Int, day: Int): Flow<List<LocalTask>> {
        return localTaskDao.getTaskByDate(year, month, day)
    }

    override suspend fun insert(task: LocalTask) {
        return localTaskDao.insert(task)
    }
}