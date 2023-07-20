package com.tasker.android.login.presentation.signup

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.common.model.server.ApplicationResponse
import com.tasker.android.common.model.server.sms.SmsSendResponse
import com.tasker.android.common.model.server.user.UserIdRes
import com.tasker.android.common.util.setSharedPrefAccessToken
import com.tasker.android.common.util.setSharedPrefRefreshToken
import com.tasker.android.domain.usecase.sms.SendSmsUseCase
import com.tasker.android.domain.usecase.user.LoginSignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sendSmsUseCase: SendSmsUseCase,
    private val loginSignupUseCase: LoginSignupUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    val etPhoneNumber = MutableStateFlow("")
    val etVerificationCode = MutableStateFlow("")

    private val _designatedPhoneNumber = MutableStateFlow("")
    val designatedPhoneNumber: StateFlow<String> get() = _designatedPhoneNumber

    fun designatePhoneNumber(number: String) {
        viewModelScope.launch {
            _designatedPhoneNumber.emit(number)
        }
    }

    fun resetVerificationCode() {
        viewModelScope.launch {
            etVerificationCode.emit("")
            _serverResponse.emit(SmsSendResponse("", ""))
        }
    }

    private val _serverResponse = MutableStateFlow(SmsSendResponse("", ""))
    val serverResponse: StateFlow<SmsSendResponse> get() = _serverResponse

    fun sendSms(number: String) {
        viewModelScope.launch {
            // 문자 발송 차단
            // 차단 해제 원할 시 -> 아래 코드 주석 해제 및 마지막 코드 삭제

//            try {
//                _serverResponse.emit(sendSmsUseCase.invoke(number))
//            } catch (e: Exception) {
//                Log.d("viewModel err", e.toString())
//            }
            _serverResponse.emit(SmsSendResponse("발송이 완료되었습니다.", ""))
        }
    }

    fun loginSignup(number: String) {
        viewModelScope.launch {
            try {
                val response = loginSignupUseCase.invoke(number)
                response.enqueue(object : Callback<ApplicationResponse<UserIdRes>> {
                    override fun onResponse(
                        call: Call<ApplicationResponse<UserIdRes>>,
                        response: Response<ApplicationResponse<UserIdRes>>,
                    ) {
                        if (response.isSuccessful) {
                            val headers = response.headers()
                            val accessToken = headers["access_token"] ?: ""
                            val refreshToken = headers["refresh_token"] ?: ""

                            saveTokens(accessToken, refreshToken)

                            val body = response.body()
                            val userId = body?.data?.userId

                        } else {
                            Log.d("call enqueue onResponse", response.isSuccessful.toString())
                        }
                    }

                    override fun onFailure(
                        call: Call<ApplicationResponse<UserIdRes>>,
                        t: Throwable,
                    ) {
                        Log.d("call enqueue onFailure", t.toString())
                    }

                })
                Log.d("response", response.toString())
            } catch (e: Exception) {
                Log.d("loginSignup err", e.toString())
            }
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        setSharedPrefAccessToken(sharedPreferences, accessToken)
        setSharedPrefRefreshToken(sharedPreferences, refreshToken)
    }
}