package com.tasker.android.data.api

import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.common.model.sms.SmsSendResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerApi {

    @POST("/v1/sms/send")
    suspend fun postSmsSend(
        @Body req: SmsSendRequest,
    ): SmsSendResponse


    @POST("/v1/users/login-signup")
    suspend fun postLoginSignup()

}