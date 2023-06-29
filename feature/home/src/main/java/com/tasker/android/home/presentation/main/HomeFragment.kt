package com.tasker.android.home.presentation.main

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeBinding
import com.tasker.android.home.presentation.date_picker.HomeDatePickerAdapter
import com.tasker.android.home.presentation.date_picker.HomeDatePickerItemDecoration
import com.tasker.android.home.model.HomeWeeklyCalendarData
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
        initDatePicker()
        initTaskViewPager()
        initComponentFunction()

        viewModel.setTaskListDummyData()
    }

    private fun initDatePicker() {
        binding.rvHomeDatePicker.apply {
            adapter = HomeDatePickerAdapter { year, month, day -> selectDate(year, month, day) }
            addItemDecoration(HomeDatePickerItemDecoration(requireContext()))
        }

        viewModel.getDatePickerList()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedDate.collect {

                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.datePickerList.collect { list ->
                    (binding.rvHomeDatePicker.adapter as HomeDatePickerAdapter)
                        .differ.submitList(list)

                    val selectedIndex = list.indexOfFirst { it.isSelected }
                    if (selectedIndex != -1) {
                        val selectedDate = list[selectedIndex]

                        binding.tvHomeYearMonth.text = getString(
                            R.string.home_year_month,
                            selectedDate.year,
                            String.format("%02d", selectedDate.month)
                        )
                    }
                }
            }
        }
    }

    private fun initTaskViewPager() {
        binding.vpHomeTaskView.adapter = HomeTaskViewAdapter(this)
        binding.vpHomeTaskView.isUserInputEnabled = false

        initTapLayout()
    }

    private fun initComponentFunction() {
        binding.ivHomeCalendar.setOnClickListener {
            findNavController().navigate(R.id.navigate_homeFragment_to_homeCalendarBottomSheetFragment)
        }
    }

    private fun initTapLayout() {
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


    private fun selectDate(year: Int, month: Int, day: Int) {
        viewModel.selectDate(HomeWeeklyCalendarData(year, month, day, true))
        viewModel.getDatePickerList()
    }
}