package com.tasker.android.data.repository

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.user.UserIdRes
import com.tasker.android.data.api.ServerApi
import com.tasker.android.domain.repository.UserRepository
import retrofit2.Call
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
) : UserRepository {

    override suspend fun loginSignup(data: SmsSendRequest): Call<ApplicationResponse<UserIdRes>> {
        return serverApi.postLoginSignup(data)
    }

    override suspend fun loginToken(token: String): Call<ApplicationResponse<UserIdRes>> {
        return serverApi.getLoginToken(token)
    }
}