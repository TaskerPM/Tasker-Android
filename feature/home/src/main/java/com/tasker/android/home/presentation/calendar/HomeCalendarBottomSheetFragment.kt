package com.tasker.android.home.presentation.calendar

import android.content.DialogInterface
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tasker.android.common.base.BaseBottomSheetDialogFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeCalendarBottomSheetBinding
import com.tasker.android.home.presentation.main.HomeViewModel
import com.tasker.android.home.model.HomeWeeklyCalendarData

class HomeCalendarBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentHomeCalendarBottomSheetBinding>(R.layout.fragment_home_calendar_bottom_sheet) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: HomeCalendarBottomSheetViewModel by viewModels()

    override fun connectViewModel() {}

    override fun init() {
        initDate()
        initComponentFunction()
        initCalendar()
    }

    private fun initDate() {
        val sd = homeViewModel.selectedDate.value
        viewModel.initDate(sd.year, sd.month, sd.day)

        setMonthYearText()
    }

    private fun initCalendar() {
        val adapter =
            HomeCalendarAdapter(
                moveBack = { moveBack() },
                moveForward = { moveForward() },
                selectDay = { day -> selectDay(day) }
            )

        binding.rvHomeCalendar.apply {
            this.layoutManager = GridLayoutManager(requireContext(), 7)
            this.setHasFixedSize(true)
            this.adapter = adapter
        }

        setCalendar()
    }

    private fun setMonthYearText() {
        binding.tvHomeCalendarYearMonth.text = getString(
            R.string.home_calendar_bottom_sheet_date,
            viewModel.selectedYear.value,
            String.format("%02d", viewModel.selectedMonth.value)
        )
    }

    private fun setCalendar() {
        (binding.rvHomeCalendar.adapter as HomeCalendarAdapter).setCalendar(
            viewModel.selectedYear.value,
            viewModel.selectedMonth.value,
            viewModel.selectedDay.value
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
            setMonthYearText()
            setCalendar()
        }
    }

    private fun moveForward() {
        viewModel.selectMonthForward()
        setMonthYearText()
        setCalendar()
    }

    private fun moveBack() {
        viewModel.selectMonthBack()
        setMonthYearText()
        setCalendar()
    }

    private fun selectDay(day: Int) {
        viewModel.selectDay(day)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        homeViewModel.selectDate(
            HomeWeeklyCalendarData(
                viewModel.selectedYear.value,
                viewModel.selectedMonth.value,
                viewModel.selectedDay.value,
                true
            )
        )
        homeViewModel.getDatePickerList()
    }
}