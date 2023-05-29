package com.tasker.android.home.presentation.task_view.list

import androidx.lifecycle.lifecycleScope
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeListViewBinding
import com.tasker.android.home.presentation.model.HomeListViewTaskData

class HomeListViewFragment :
    BaseFragment<FragmentHomeListViewBinding>(R.layout.fragment_home_list_view) {

    private val homeListViewAdapter by lazy { HomeListViewAdapter() }

    override fun connectViewModel() {

    }

    override fun init() {
        initListView()
    }

    private fun initListView() {
        binding.rvHomeListView.adapter = homeListViewAdapter
        binding.rvHomeListView.addItemDecoration(HomeListViewItemDecoration(requireContext()))

        //set dummy data
        val list = listOf(
            HomeListViewTaskData("IA 구조도 그리기", false, true, "스터디", "14:00-17:00"),
            HomeListViewTaskData("와이어프레임 제작-lofi", false, false, "", ""),
            HomeListViewTaskData("IA 구조도 그리기", true, true, "스터디", "14:00-17:00"),
            HomeListViewTaskData("와이어프레임 제작-lofi", true, false, "", ""),
        )

        lifecycleScope.launchWhenStarted {
            homeListViewAdapter.submitList(list)
        }
    }
}