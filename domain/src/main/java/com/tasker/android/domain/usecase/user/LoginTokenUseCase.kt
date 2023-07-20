package com.tasker.android.domain.usecase.user

import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.user.UserIdRes
import com.tasker.android.domain.repository.UserRepository
import retrofit2.Call
import javax.inject.Inject

class LoginTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(token: String): Call<ApplicationResponse<UserIdRes>> {
        return userRepository.loginToken(token)
    }
}