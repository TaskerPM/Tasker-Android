package com.tasker.android.login.presentation.onboarding

import androidx.navigation.fragment.findNavController
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.login.R
import com.tasker.android.login.databinding.FragmentOnboardingBinding
import com.tasker.android.login.presentation.onboarding.viewpager.OnboardingAdapter

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    override fun connectViewModel() {}

    override fun init() {
        initComponentFunction()
        setViewPager()
    }

    private fun initComponentFunction() {
        binding.btnOnboardingStart.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_signUpFragment)
        }
    }

    private fun setViewPager() {
        binding.apply {
            vpOnboarding.adapter = OnboardingAdapter(
                listOf(
                    R.drawable.img_onboarding_1,
                    R.drawable.img_onboarding_2,
                    R.drawable.img_onboarding_3,
                )
            )
            diOnboarding.attachTo(vpOnboarding)
        }
    }
}