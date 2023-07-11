package com.tasker.android.login.presentation.signup

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.common.constants.Constants
import com.tasker.android.login.R
import com.tasker.android.login.activity.LoginActivity
import com.tasker.android.login.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
//        initSmsRetriever()
        initComponentFunction()
        initCollectors()

        //temporarily added
//        startMainActivity()
    }

    private fun initSmsRetriever() {
        SmsRetriever
            .getClient(requireContext())
            .startSmsUserConsent(binding.etSignupPhoneNumber.text.toString())
            .also {
                it
                    .addOnSuccessListener {

                    }.addOnFailureListener {

                    }
            }
    }

    private fun initComponentFunction() {
        binding.apply {
            btnSignupGetVerificationCode.setOnClickListener {
                viewModel!!.sendSms(binding.etSignupPhoneNumber.text.toString())
                viewModel!!.designatePhoneNumber(binding.etSignupPhoneNumber.text.toString())
            }

            btnSignupStart.setOnClickListener {
                startMainActivity()
            }
        }
    }

    private fun initCollectors() {
        binding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.etPhoneNumber.collect {
                        if (it.length == 11) {
                            btnSignupGetVerificationCode.isEnabled = true
                            tvSignupPhoneInputWarning.visibility = View.GONE
                        } else {
                            btnSignupGetVerificationCode.isEnabled = false
                        }

                        if (it != viewModel!!.designatedPhoneNumber.value) {
                            switchUIMode(isMessageSent = false)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.etPhoneNumber.debounce(1000).collect {
                        if (it.length != 11 && it.isNotEmpty()) {
                            tvSignupPhoneInputWarning.visibility = View.VISIBLE
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.serverResponse.collect {
                        if (it.message == "발송이 완료되었습니다.") {
                            switchUIMode(isMessageSent = true)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.etVerificationCode.collect {
                        if (it == (viewModel!!.serverResponse.value.value)) {
                            btnSignupStart.isEnabled = true
                            tvSignupCodeInputWarning.visibility = View.GONE
                        } else {
                            btnSignupStart.isEnabled = false
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel!!.etVerificationCode.debounce(1000).collect {
                        if (it != (viewModel!!.serverResponse.value.value)) {
                            tvSignupCodeInputWarning.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun switchUIMode(isMessageSent: Boolean) {
        binding.apply {
            when (isMessageSent) {
                true -> {
                    etSignupVerificationCode.visibility = View.VISIBLE
                    tvSignupTimeout.visibility = View.VISIBLE
                    llSignupTerms.visibility = View.VISIBLE
                    btnSignupStart.visibility = View.VISIBLE

                    btnSignupGetVerificationCode.isEnabled = false
                }
                false -> {
                    etSignupVerificationCode.visibility = View.GONE
                    tvSignupTimeout.visibility = View.GONE
                    llSignupTerms.visibility = View.GONE
                    btnSignupStart.visibility = View.GONE
                    tvSignupCodeInputWarning.visibility = View.GONE

                    viewModel!!.resetVerificationCode()
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent()
        intent.setClassName(
            requireContext(),
            "com.tasker.android.app.view.MainActivity"
        )
        intent.putExtra("from", Constants.LAUNCH_FROM_LOGIN_TO_MAIN)
        startActivity(intent)

        (activity as LoginActivity).finish()
    }
}