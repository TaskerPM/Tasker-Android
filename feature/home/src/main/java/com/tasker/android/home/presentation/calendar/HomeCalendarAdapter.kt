package com.tasker.android.home.presentation.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.R
import com.tasker.android.home.databinding.ItemHomeCalendarBinding
import com.tasker.android.home.presentation.model.HomeCalendarDate
import java.time.LocalDate

class HomeCalendarAdapter(private val moveBack: () -> Unit, private val moveForward: () -> Unit) :
    RecyclerView.Adapter<HomeCalendarAdapter.HomeCalendarViewHolder>() {

    lateinit var context: Context

    private val currentDate = LocalDate.now()
    private val dateList = mutableListOf<HomeCalendarDate>()
    private var startDayPosition = 0
    private var endDayPosition = 0
    private var selectedItemPosition = RecyclerView.NO_POSITION

    fun setCalendar(year: Int, month: Int) {
        val selectedMonthDate = LocalDate.of(year, month, 1)
        val previousMonthDate = selectedMonthDate.minusMonths(1)
        val nextMonthDate = selectedMonthDate.plusMonths(1)

        val lastDateOfPreviousMonth = previousMonthDate.lengthOfMonth()
        val daysFromPreviousMonth = selectedMonthDate.dayOfWeek.value - 1

        dateList.clear()

        for (i in 0 until daysFromPreviousMonth) {
            val previousDay = lastDateOfPreviousMonth - daysFromPreviousMonth + i + 1
            dateList.add(
                HomeCalendarDate(
                    previousMonthDate.year,
                    previousMonthDate.monthValue,
                    previousDay
                )
            )
        }

        for (i in 1..selectedMonthDate.lengthOfMonth()) {
            dateList.add(
                HomeCalendarDate(
                    selectedMonthDate.year,
                    selectedMonthDate.monthValue,
                    i
                )
            )
        }

        val remainingDays = 42 - dateList.size
        for (i in 1..remainingDays) {
            dateList.add(
                HomeCalendarDate(
                    nextMonthDate.year,
                    nextMonthDate.monthValue,
                    i
                )
            )
        }

        startDayPosition = daysFromPreviousMonth
        endDayPosition = startDayPosition + selectedMonthDate.lengthOfMonth() - 1

        selectedItemPosition = if (month == currentDate.monthValue) {
            startDayPosition + 7 + currentDate.dayOfMonth - 1
        } else {
            startDayPosition + 7
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCalendarViewHolder {
        context = parent.context

        return HomeCalendarViewHolder(
            ItemHomeCalendarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = 7 + 42

    override fun onBindViewHolder(holder: HomeCalendarViewHolder, position: Int) {
        holder.bind()
    }

    inner class HomeCalendarViewHolder(private val binding: ItemHomeCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            initCalendarDates()
            initSelectedItem()
            initClickFunction()
        }

        private fun initCalendarDates() {
            binding.apply {
                tvHomeCalendarDate.background = null

                when (adapterPosition) {
                    0 -> tvHomeCalendarDate.text = "월"
                    1 -> tvHomeCalendarDate.text = "화"
                    2 -> tvHomeCalendarDate.text = "수"
                    3 -> tvHomeCalendarDate.text = "목"
                    4 -> tvHomeCalendarDate.text = "금"
                    5 -> tvHomeCalendarDate.text = "토"
                    6 -> tvHomeCalendarDate.text = "일"
                    else -> {
                        tvHomeCalendarDate.text = dateList[adapterPosition - 7].day.toString()

                        val isCurrentMonth =
                            adapterPosition - 7 in (startDayPosition..endDayPosition)

                        if (isCurrentMonth) {
                            tvHomeCalendarDate.setTextColor(
                                getColorFrom(
                                    context,
                                    com.tasker.android.common.R.color.gray_800
                                )
                            )
                        } else {
                            tvHomeCalendarDate.setTextColor(
                                getColorFrom(
                                    context,
                                    com.tasker.android.common.R.color.gray_400
                                )
                            )
                        }
                    }
                }
            }
        }

        private fun initSelectedItem() {
            if (adapterPosition == selectedItemPosition) {
                setSelectedItem()
            }
        }

        private fun initClickFunction() {
            binding.apply {
                tvHomeCalendarDate.setOnClickListener {
                    when (adapterPosition) {
                        in 0..6 -> {}

                        in 7 until startDayPosition + 7 -> {
                            moveBack()
                        }

                        in (endDayPosition + 7 + 1) until itemCount -> {
                            moveForward()
                        }

                        else -> {
                            selectItem()
                        }
                    }
                }
            }
        }

        private fun selectItem() {
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = adapterPosition

            if (previousSelectedPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(previousSelectedPosition)
            }

            notifyItemChanged(selectedItemPosition)
        }

        private fun setSelectedItem() {
            binding.tvHomeCalendarDate.apply {
                this.background =
                    getDrawableFrom(context, R.drawable.background_home_calendar_selected)

                this.setTextColor(
                    getColorFrom(context, com.tasker.android.common.R.color.white)
                )
            }
        }
    }
}