package com.tasker.android.home.presentation.home.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.getDayOfWeekShortString
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.R
import com.tasker.android.home.databinding.ItemHomeCalendarBinding
import com.tasker.android.home.model.HomeCalendarDate
import java.time.LocalDate

class HomeCalendarAdapter(
    private val moveBack: () -> Unit,
    private val moveForward: () -> Unit,
    private val selectDay: (Int) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_WEEKDAY = 0
        const val VIEW_TYPE_CALENDAR = 1
    }

    lateinit var context: Context

    private val dateList = mutableListOf<HomeCalendarDate>()
    private var startDayPosition = 0
    private var endDayPosition = 0
    var selectedItemPosition = RecyclerView.NO_POSITION

    @SuppressLint("NotifyDataSetChanged")
    fun setCalendar(year: Int, month: Int, day: Int) {
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

        // exclude weekday headers
        startDayPosition = daysFromPreviousMonth
        endDayPosition = startDayPosition + selectedMonthDate.lengthOfMonth() - 1
        selectedItemPosition =
            if ((startDayPosition + day - 1) <= selectedMonthDate.lengthOfMonth()) {
                startDayPosition + day - 1
            } else {
                startDayPosition + selectedMonthDate.lengthOfMonth() - 1
            }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        return when (viewType) {
            VIEW_TYPE_WEEKDAY -> {
                val binding = ItemHomeCalendarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                WeekdayViewHolder(binding)
            }
            else -> {
                val binding = ItemHomeCalendarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CalendarViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 7) {
            VIEW_TYPE_WEEKDAY
        } else {
            VIEW_TYPE_CALENDAR
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeekdayViewHolder -> holder.bind(position)
            is CalendarViewHolder -> holder.bind(position - 7)
        }
    }

    override fun getItemCount(): Int = 7 + dateList.size

    inner class WeekdayViewHolder(private val binding: ItemHomeCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weekDayIndex: Int) {
            binding.tvHomeCalendarDate.text = getDayOfWeekShortString(weekDayIndex + 1)
        }
    }

    inner class CalendarViewHolder(private val binding: ItemHomeCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var index: Int? = null

        fun bind(index: Int) {
            this.index = index
            binding.tvHomeCalendarDate.text = dateList[index].day.toString()

            initClickFunction()
            initUnselectedItem()
            initSelectedItem()
        }

        private fun initClickFunction() {
            binding.apply {
                tvHomeCalendarDate.setOnClickListener {
                    when (index) {
                        in 0 until startDayPosition -> {
                            moveBack()
                        }

                        in (endDayPosition + 1) until itemCount -> {
                            moveForward()
                        }

                        else -> {
                            selectItem()
                        }
                    }
                }
            }
        }

        private fun initUnselectedItem() {
            val isCurrentMonth =
                index in (startDayPosition..endDayPosition)

            binding.tvHomeCalendarDate.apply {
                this.background = null
                if (isCurrentMonth) {
                    binding.tvHomeCalendarDate.setTextColor(
                        getColorFrom(
                            context,
                            com.tasker.android.common.R.color.gray_800
                        )
                    )
                } else {
                    binding.tvHomeCalendarDate.setTextColor(
                        getColorFrom(
                            context,
                            com.tasker.android.common.R.color.gray_400
                        )
                    )
                }
            }
        }

        private fun initSelectedItem() {
            if (index == selectedItemPosition) {
                binding.tvHomeCalendarDate.apply {
                    this.background =
                        getDrawableFrom(context, R.drawable.bg_calendar_selected)

                    this.setTextColor(
                        getColorFrom(context, com.tasker.android.common.R.color.white)
                    )
                }
            }
        }

        private fun selectItem() {
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = index!!

            if (previousSelectedPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(previousSelectedPosition + 7)
            }
            notifyItemChanged(selectedItemPosition + 7)

            val selectedDay = selectedItemPosition - startDayPosition + 1
            selectDay(selectedDay)
        }
    }
}