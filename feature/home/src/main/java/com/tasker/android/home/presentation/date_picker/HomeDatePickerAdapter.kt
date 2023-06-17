package com.tasker.android.home.presentation.date_picker

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.getDayOfWeekShortString
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.R
import com.tasker.android.home.databinding.ItemHomeDatePickerBinding
import com.tasker.android.home.presentation.date_picker.HomeDatePickerAdapter.HomeDatePickerViewHolder
import com.tasker.android.home.presentation.model.HomeWeeklyCalendarData


class HomeDatePickerAdapter(private val selectDate: (Int, Int, Int) -> Unit) :
    RecyclerView.Adapter<HomeDatePickerViewHolder>() {

    lateinit var context: Context
    var width: Int = 0

    private val diffUtil = object : DiffUtil.ItemCallback<HomeWeeklyCalendarData>() {
        override fun areItemsTheSame(
            oldItem: HomeWeeklyCalendarData, newItem: HomeWeeklyCalendarData,
        ): Boolean = oldItem.day == newItem.day


        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: HomeWeeklyCalendarData, newItem: HomeWeeklyCalendarData,
        ): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeDatePickerViewHolder {
        context = parent.context
        width =
            ((parent.measuredWidth.toFloat()
                    - (6 * parent.context.resources.getDimension(R.dimen.home_date_picker_item_margin))) / 7).toInt()

        return HomeDatePickerViewHolder(
            ItemHomeDatePickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: HomeDatePickerViewHolder,
        position: Int,
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int = 7

    inner class HomeDatePickerViewHolder(private val binding: ItemHomeDatePickerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var item: HomeWeeklyCalendarData

        fun bind() {
            item = differ.currentList[adapterPosition]

            initLayoutParams()
            initText()
            initClickFunction()
            initSelectedItem()
        }

        private fun initText() {
            binding.tvHomeDatePickerDay.text = getDayOfWeekShortString(
                item.year,
                item.month,
                item.day
            )
            binding.tvHomeDatePickerDate.text = String.format("%02d", item.day)
        }

        private fun initLayoutParams() {
            binding.root.layoutParams = ViewGroup.LayoutParams(width, width / 2 * 3)
        }

        private fun initSelectedItem() {
            setSelectedDateBackground(isSelected = item.isSelected)
        }

        private fun initClickFunction() {
            binding.clHomeDatePicker.setOnClickListener {
                selectItem()
            }
        }

        private fun setSelectedDateBackground(isSelected: Boolean) {
            binding.apply {
                when (isSelected) {
                    true -> {
                        tvHomeDatePickerDay.setTextColor(
                            context.getColor(com.tasker.android.common.R.color.white)
                        )

                        tvHomeDatePickerDate.setTextColor(
                            context.getColor(com.tasker.android.common.R.color.white)
                        )

                        clHomeDatePicker.background = getDrawableFrom(
                            context,
                            R.drawable.background_item_home_date_picker_selected
                        )
                    }

                    false -> {
                        tvHomeDatePickerDay.setTextColor(
                            context.getColor(com.tasker.android.common.R.color.black_basic)
                        )

                        tvHomeDatePickerDate.setTextColor(
                            context.getColor(com.tasker.android.common.R.color.black_basic)
                        )

                        clHomeDatePicker.background = null
                    }
                }
            }
        }

        private fun selectItem() {
            selectDate(item.year, item.month, item.day)
        }
    }
}