package com.tasker.android.domain.usecase.local

import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.domain.repository.LocalTaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalTaskUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository,
) {
    operator fun invoke(year: Int, month: Int, day: Int): Flow<List<LocalTask>> {
        return localTaskRepository.getTaskByDate(year, month, day)
    }
}