package com.tasker.android.domain.usecase.task

import com.tasker.android.common.di.IoDispatcher
import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.task.req.PostTaskReq
import com.tasker.android.common.model.server.task.res.PostTaskRes
import com.tasker.android.domain.repository.TaskRepository
import com.tasker.android.domain.usecase.base.ResultWrapper
import com.tasker.android.domain.usecase.base.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PostTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(
        accessToken: String,
        date: String,
        data: PostTaskReq,
    ): ResultWrapper<ApplicationResponse<PostTaskRes>> {
        return safeApiCall(ioDispatcher) { taskRepository.postTask(accessToken, date, data) }
    }
}