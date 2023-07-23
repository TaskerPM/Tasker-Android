package com.tasker.android.domain.usecase.sms

import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.sms.SmsSendResponse
import com.tasker.android.domain.repository.SmsRepository
import javax.inject.Inject

class SendSmsUseCase @Inject constructor(
    private val smsRepository: SmsRepository,
) {
    suspend operator fun invoke(number: String): SmsSendResponse {
        return smsRepository.sendSms(data = SmsSendRequest(number))
    }
}