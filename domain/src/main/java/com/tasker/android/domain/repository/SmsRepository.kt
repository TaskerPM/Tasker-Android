package com.tasker.android.domain.repository

import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.sms.SmsSendResponse

interface SmsRepository {

    suspend fun sendSms(data: SmsSendRequest): SmsSendResponse
}