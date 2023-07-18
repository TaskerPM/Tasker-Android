package com.tasker.android.data.api

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.sms.SmsSendResponse
import com.tasker.android.common.model.server.task.req.PostTaskReq
import com.tasker.android.common.model.server.task.res.GetTaskRes
import com.tasker.android.common.model.server.task.res.PostTaskRes
import com.tasker.android.common.model.server.user.UserIdRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {

    // SMS API

    @POST("/v1/sms/send")
    suspend fun postSmsSend(
        @Body req: SmsSendRequest,
    ): SmsSendResponse

    // USER API

    @POST("/v1/users/login-signup")
    fun postLoginSignup(
        @Body req: SmsSendRequest,
    ): Call<ApplicationResponse<UserIdRes>>

    @GET("/v1/users/loginToken")
    fun getLoginToken(
        @Header("X-REFRESH-TOKEN") refreshToken: String,
    ): Call<ApplicationResponse<UserIdRes>>

    // TASK API

    @GET("/v1/task/home/{date}")
    fun getTaskByDate(
        @Header("X-ACCESS-TOKEN") accessToken: String,
        @Path("date") date: String,
    ): ApplicationResponse<List<GetTaskRes>>

    @POST("/v1/task/home/{date}")
    suspend fun postTask(
        @Header("X-ACCESS-TOKEN") accessToken: String,
        @Path("date") date: String,
        @Body req: PostTaskReq,
    ): Response<ApplicationResponse<PostTaskRes>>
}