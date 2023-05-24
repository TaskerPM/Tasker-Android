package com.tasker.android.home.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.R
import com.tasker.android.home.databinding.ItemHomeDatePickerBinding
import com.tasker.android.home.presentation.home.HomeDatePickerAdapter.HomeDatePickerViewHolder
import com.tasker.android.home.presentation.model.DayNDate


class HomeDatePickerAdapter(private val list: List<DayNDate>) :
    RecyclerView.Adapter<HomeDatePickerViewHolder>() {

    lateinit var context: Context
    var width: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeDatePickerViewHolder {
        context = parent.context
        width =
            ((parent.measuredWidth.toFloat()
                    - (6 * parent.context.resources.getDimension(R.dimen.home_date_picker_margin))) / 7).toInt()

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

        fun bind() {
            binding.root.layoutParams = ViewGroup.LayoutParams(width, width / 2 * 3)

            binding.tvHomeDatePickerDay.text = list[adapterPosition].day
            binding.tvHomeDatePickerDate.text = String.format("%02d", list[adapterPosition].date)

            // handle selected item view
            if (adapterPosition == 1) {
                binding.tvHomeDatePickerDay.setTextColor(
                    context.getColor(com.tasker.android.common.R.color.white_ff)
                )

                binding.tvHomeDatePickerDate.setTextColor(
                    context.getColor(com.tasker.android.common.R.color.white_ff)
                )

                binding.clHomeDatePicker.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.background_home_item_date_picker_selected,
                    null
                )
            }
        }
    }
}