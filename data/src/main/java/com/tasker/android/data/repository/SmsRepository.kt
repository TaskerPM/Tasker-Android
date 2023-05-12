package com.tasker.android.data.repository

import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.common.model.sms.SmsSendResponse

interface SmsRepository {

    suspend fun sendSms(data: SmsSendRequest): SmsSendResponse
}