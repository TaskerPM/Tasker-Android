package com.tasker.android.data.repository

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.task.req.PostTaskReq
import com.tasker.android.common.model.server.task.res.GetTaskRes
import com.tasker.android.common.model.server.task.res.PostTaskRes
import com.tasker.android.data.api.ServerApi
import com.tasker.android.domain.repository.TaskRepository
import retrofit2.Response
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
) : TaskRepository {

    override suspend fun getTaskByDate(
        accessToken: String,
        date: String,
    ): ApplicationResponse<List<GetTaskRes>> {
        return serverApi.getTaskByDate(accessToken, date)
    }

    override suspend fun postTask(
        accessToken: String,
        date: String,
        data: PostTaskReq,
    ): Response<ApplicationResponse<PostTaskRes>> {
        return serverApi.postTask(accessToken, date, data)
    }
}