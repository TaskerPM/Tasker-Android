package com.tasker.android.login.presentation.splash

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.login.R
import com.tasker.android.login.databinding.FragmentSplashBinding
import com.tasker.android.login.util.startMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun connectViewModel() {

    }

    override fun init() {
        viewModel.verifyRefreshToken()

        Handler(Looper.getMainLooper()).postDelayed({
            startMainActivity(requireContext(), requireActivity())
        }, 2000)
    }

}