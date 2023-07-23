package com.tasker.android.login.presentation.splash

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.user.UserIdRes
import com.tasker.android.common.util.getSharedPrefRefreshToken
import com.tasker.android.domain.usecase.user.LoginTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginTokenUseCase: LoginTokenUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _isTokenValid = MutableStateFlow(false)
    val isTokenValid get() = _isTokenValid

    fun verifyRefreshToken() {
        viewModelScope.launch {
            val refreshToken = getSharedPrefRefreshToken(sharedPreferences)

            val response = loginTokenUseCase.invoke(refreshToken)
            response.enqueue(object : Callback<ApplicationResponse<UserIdRes>> {
                override fun onResponse(
                    call: Call<ApplicationResponse<UserIdRes>>,
                    response: Response<ApplicationResponse<UserIdRes>>,
                ) {
                    if (response.isSuccessful) {
                        Log.d("login Token", response.body().toString())

                    }
                }

                override fun onFailure(call: Call<ApplicationResponse<UserIdRes>>, t: Throwable) {

                }

            })
        }
    }
}