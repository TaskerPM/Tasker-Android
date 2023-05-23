package com.tasker.android.login.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasker.android.common.model.sms.SmsSendResponse
import com.tasker.android.domain.usecase.sms.SendSmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sendSmsUseCase: SendSmsUseCase,
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
            try {
                _serverResponse.emit(sendSmsUseCase.invoke(number))
            } catch (e: Exception) {
                Log.d("viewModel err", e.toString())
            }
        }
    }
}