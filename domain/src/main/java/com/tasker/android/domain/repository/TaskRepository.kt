package com.tasker.android.domain.repository

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.task.req.PostTaskReq
import com.tasker.android.common.model.server.task.res.GetTaskRes
import com.tasker.android.common.model.server.task.res.PostTaskRes
import retrofit2.Response

interface TaskRepository {

    suspend fun getTaskByDate(accessToken: String, date: String) : ApplicationResponse<List<GetTaskRes>>
    suspend fun postTask(accessToken: String, date: String, data: PostTaskReq) : Response<ApplicationResponse<PostTaskRes>>
}