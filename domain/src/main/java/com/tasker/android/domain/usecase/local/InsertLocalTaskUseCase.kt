package com.tasker.android.domain.usecase.local

import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.domain.repository.LocalTaskRepository
import javax.inject.Inject

class InsertLocalTaskUseCase @Inject constructor(
    private val localTaskRepository: LocalTaskRepository,
) {
    suspend operator fun invoke(task: LocalTask) {
        return localTaskRepository.insert(task)
    }
}