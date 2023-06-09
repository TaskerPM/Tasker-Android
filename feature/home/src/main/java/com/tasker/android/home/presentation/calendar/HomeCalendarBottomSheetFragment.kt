package com.tasker.android.home.presentation.calendar

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tasker.android.common.base.BaseBottomSheetDialogFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeCalendarBottomSheetBinding

class HomeCalendarBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentHomeCalendarBottomSheetBinding>(R.layout.fragment_home_calendar_bottom_sheet) {

    private val viewModel: HomeCalendarBottomSheetViewModel by viewModels()

    override fun connectViewModel() {}

    override fun init() {
        initDateText()
        initComponentFunction()
        initCalendar()
    }

    private fun initDateText() {
        binding.tvHomeCalendarYearMonth.text = getString(
            R.string.home_calendar_bottom_sheet_date,
            viewModel.selectedYear.value,
            String.format("%02d", viewModel.selectedMonth.value)
        )
    }

    private fun initCalendar() {
        val adapter =
            HomeCalendarAdapter(moveBack = { moveBack() }, moveForward = { moveForward() })

        binding.rvHomeCalendar.apply {
            this.layoutManager = GridLayoutManager(requireContext(), 7)
            this.adapter = adapter
        }

        setCalendar()
    }

    private fun setCalendar() {
        (binding.rvHomeCalendar.adapter as HomeCalendarAdapter).setCalendar(
            viewModel.selectedYear.value,
            viewModel.selectedMonth.value,
        )
    }

    private fun initComponentFunction() {
        binding.ivHomeCalendarBack.setOnClickListener {
            moveBack()
        }

        binding.ivHomeCalendarForward.setOnClickListener {
            moveForward()
        }

        binding.tvHomeCalendarToday.setOnClickListener {
            viewModel.selectToday()
            initDateText()

            binding.rvHomeCalendar.apply {
                (this.adapter as HomeCalendarAdapter).setCalendar(
                    viewModel.selectedYear.value,
                    viewModel.selectedMonth.value,
                )
            }
        }
    }

    private fun moveBack() {
        viewModel.selectBack()
        initDateText()
        setCalendar()
    }

    private fun moveForward() {
        viewModel.selectForward()
        initDateText()
        setCalendar()
    }
}