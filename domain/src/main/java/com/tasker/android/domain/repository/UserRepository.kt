package com.tasker.android.domain.repository

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.user.UserIdRes
import retrofit2.Call

interface UserRepository {

    suspend fun loginSignup(data: SmsSendRequest): Call<ApplicationResponse<UserIdRes>>
    suspend fun loginToken(token: String): Call<ApplicationResponse<UserIdRes>>
}