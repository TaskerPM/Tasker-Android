package com.tasker.android.domain.usecase.user

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.sms.SmsSendRequest
import com.tasker.android.common.model.server.user.UserIdRes
import com.tasker.android.domain.repository.UserRepository
import retrofit2.Call
import javax.inject.Inject

class LoginSignupUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(phoneNum: String): Call<ApplicationResponse<UserIdRes>> {
        return userRepository.loginSignup(data = SmsSendRequest(phoneNum))
    }
}