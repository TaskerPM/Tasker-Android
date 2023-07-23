package com.tasker.android.domain.usecase.task

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.task.res.GetTaskRes
import com.tasker.android.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(
        accessToken: String,
        date: String,
    ): ApplicationResponse<List<GetTaskRes>> {
        return taskRepository.getTaskByDate(accessToken, date)
    }
}