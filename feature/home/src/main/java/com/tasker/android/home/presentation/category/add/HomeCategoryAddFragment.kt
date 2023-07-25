package com.tasker.android.home.presentation.category.add

import android.view.View
import androidx.navigation.fragment.findNavController
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeCategoryAddBinding

class HomeCategoryAddFragment :
    BaseFragment<FragmentHomeCategoryAddBinding>(R.layout.fragment_home_category_add) {
    override fun connectViewModel() {
    }

    override fun init() {
        initMenu()
        initBackNavigation()
    }

    private fun initMenu() {
        binding.tbHomeCategoryAdd.tvTitle.text = getString(R.string.home_cateogry_add_title)
        binding.tbHomeCategoryAdd.tvBtnRight.visibility = View.VISIBLE
        binding.tbHomeCategoryAdd.tvBtnRight.text = getString(R.string.home_category_add_finish)
    }

    private fun initBackNavigation() {
        binding.tbHomeCategoryAdd.tbBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}