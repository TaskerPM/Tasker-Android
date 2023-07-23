package com.tasker.android.home.presentation.category.main

import android.view.View
import androidx.navigation.fragment.findNavController
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeCategoryBinding

class HomeCategoryFragment : BaseFragment<FragmentHomeCategoryBinding>(R.layout.fragment_home_category) {

    override fun connectViewModel() {
    }

    override fun init() {
        initBackNavigation()
        initMenu()
        initComponentFunction()
    }

    private fun initMenu() {
        binding.tbHomeCategory.tvTitle.text = getString(R.string.home_category_title)
        binding.tbHomeCategory.tbBtnRight.visibility = View.VISIBLE
    }

    private fun initBackNavigation() {
        binding.tbHomeCategory.tbBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initComponentFunction() {
        binding.tbHomeCategory.tbBtnRight.setOnClickListener {
            findNavController().navigate(R.id.action_homeCategoryFragment_to_homeCategoryAddFragment)
        }
    }


}