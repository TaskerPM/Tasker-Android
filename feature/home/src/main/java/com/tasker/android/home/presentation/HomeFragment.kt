package com.tasker.android.home.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeBinding
import com.tasker.android.home.presentation.date_picker.HomeDatePickerAdapter
import com.tasker.android.home.presentation.date_picker.HomeDatePickerItemDecoration
import com.tasker.android.home.presentation.task_view.HomeTaskViewAdapter
import com.tasker.android.home.presentation.model.HomeWeeklyCalendarData

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
        initRecyclerView()
        initViewPager()
        initComponentFunction()
    }

    private fun initRecyclerView() {
        binding.rvHomeDatePicker.apply {
            adapter = HomeDatePickerAdapter(
                list = listOf(
                    HomeWeeklyCalendarData("월", 30),
                    HomeWeeklyCalendarData("화", 31),
                    HomeWeeklyCalendarData("수", 1),
                    HomeWeeklyCalendarData("목", 2),
                    HomeWeeklyCalendarData("금", 3),
                    HomeWeeklyCalendarData("토", 4),
                    HomeWeeklyCalendarData("일", 5),
                )
            )

            addItemDecoration(HomeDatePickerItemDecoration(requireContext()))
        }
    }

    private fun initViewPager() {
        binding.vpHomeTaskView.adapter = HomeTaskViewAdapter(this)
        binding.vpHomeTaskView.isUserInputEnabled = false
        TabLayoutMediator(binding.tlHomeViewType, binding.vpHomeTaskView) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.icon_home_list_selector)
                }
                1 -> {
                    tab.setIcon(R.drawable.icon_home_time_selector)
                }
                2 -> {
                    tab.setIcon(R.drawable.icon_home_category_selector)
                }
            }
        }.attach()
    }


    private fun initComponentFunction() {
        binding.ivHomeCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeCalendarBottomSheetFragment)
        }
    }
}