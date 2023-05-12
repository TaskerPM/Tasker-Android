package com.tasker.android.data.repository

import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.common.model.sms.SmsSendResponse
import com.tasker.android.data.api.ServerApi
import javax.inject.Inject

class SmsRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
) : SmsRepository {
    override suspend fun sendSms(data: SmsSendRequest): SmsSendResponse {
        return serverApi.postSmsSend(data)
    }
}