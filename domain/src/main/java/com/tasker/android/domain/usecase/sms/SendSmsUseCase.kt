package com.tasker.android.domain.usecase.sms

import androidx.lifecycle.LiveData
import com.tasker.android.common.model.sms.SmsSendRequest
import com.tasker.android.common.model.sms.SmsSendResponse
import com.tasker.android.data.repository.SmsRepository
import javax.inject.Inject

class SendSmsUseCase @Inject constructor(
    private val smsRepository: SmsRepository,
) {
    suspend operator fun invoke(number: String): SmsSendResponse {
        return smsRepository.sendSms(SmsSendRequest(number))
    }
}