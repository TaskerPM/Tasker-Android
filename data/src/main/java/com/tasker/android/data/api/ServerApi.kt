package com.tasker.android.data.api

import com.tasker.android.common.model.ApplicationResponse
import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.common.model.sms.SmsSendResponse
import com.tasker.android.common.model.task.res.PostTaskRes
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {

    @POST("/v1/sms/send")
    suspend fun postSmsSend(
        @Body req: SmsSendRequest,
    ): SmsSendResponse


    @POST("/v1/users/login-signup")
    suspend fun postLoginSignup()

    @POST("/v1/task/home/{date}")
    suspend fun postTask(@Path("date") date: String): ApplicationResponse<PostTaskRes>
}