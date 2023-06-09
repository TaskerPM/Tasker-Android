package com.tasker.android.home.presentation.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.R
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.presentation.model.HomeTaskData

class HomeListViewAdapter :
    ListAdapter<HomeTaskData, HomeListViewAdapter.HomeListViewHolder>(HomeListViewDiffUtil()) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {

        context = parent.context

        return HomeListViewHolder(
            ItemHomeListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomeListViewHolder(private val binding: ItemHomeListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HomeTaskData) {
            binding.apply {
                tvHomeTaskText.text = data.text

                when (data.isCompleted) {
                    true -> {
                        ivHomeTaskIsCompleted.setImageResource(R.drawable.icon_home_task_completed)
                        vHomeTaskIsCompleted.visibility = View.VISIBLE
                        tvHomeTaskText.setTextColor(
                            getColorFrom(context, com.tasker.android.common.R.color.gray_230)
                        )

                        if (data.categoryTag.isNotEmpty()) {
                            tvHomeTaskTagCategory.text = data.categoryTag
                            DrawableCompat.setTint(
                                DrawableCompat.wrap(tvHomeTaskTagCategory.background),
                                ContextCompat.getColor(
                                    context,
                                    com.tasker.android.common.R.color.gray_30
                                )
                            )
                            tvHomeTaskTagCategory.setTextColor(
                                getColorFrom(context, com.tasker.android.common.R.color.gray_230)
                            )
                        }

                        if (data.timeTag.isNotEmpty()) {
                            tvHomeTaskTagTime.text = data.timeTag
                            DrawableCompat.setTint(
                                DrawableCompat.wrap(tvHomeTaskTagTime.background),
                                ContextCompat.getColor(
                                    context,
                                    com.tasker.android.common.R.color.gray_30
                                )
                            )
                            tvHomeTaskTagTime.setTextColor(
                                getColorFrom(context, com.tasker.android.common.R.color.gray_230)
                            )
                        }
                    }
                    false -> {
                        ivHomeTaskIsCompleted.setImageResource(R.drawable.icon_home_task_uncompleted)
                        vHomeTaskIsCompleted.visibility = View.GONE
                        tvHomeTaskText.setTextColor(
                            getColorFrom(context, com.tasker.android.common.R.color.black_basic)
                        )

                        if (data.categoryTag.isNotEmpty()) {
                            tvHomeTaskTagCategory.text = data.categoryTag
                            DrawableCompat.setTint(
                                DrawableCompat.wrap(tvHomeTaskTagCategory.background),
                                ContextCompat.getColor(
                                    context,
                                    com.tasker.android.common.R.color.temp_tag_category
                                )
                            )
                            tvHomeTaskTagCategory.setTextColor(
                                getColorFrom(
                                    context,
                                    com.tasker.android.common.R.color.temp_tag_text_category
                                )
                            )
                        }

                        if (data.timeTag.isNotEmpty()) {
                            tvHomeTaskTagTime.text = data.timeTag
                            DrawableCompat.setTint(
                                DrawableCompat.wrap(tvHomeTaskTagTime.background),
                                ContextCompat.getColor(
                                    context,
                                    com.tasker.android.common.R.color.temp_tag_time
                                )
                            )
                            tvHomeTaskTagTime.setTextColor(
                                getColorFrom(
                                    context,
                                    com.tasker.android.common.R.color.temp_tag_text_time
                                )
                            )
                        }
                    }
                }

                when (data.isFilled) {
                    true -> {
                        clHomeTask.background =
                            getDrawableFrom(context, R.drawable.background_item_home_task_filled)
                    }
                    false -> {
                        clHomeTask.background =
                            getDrawableFrom(context, R.drawable.background_item_home_task_unfilled)
                    }
                }
            }
        }
    }

    private class HomeListViewDiffUtil : DiffUtil.ItemCallback<HomeTaskData>() {
        override fun areItemsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
        ): Boolean = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
        ): Boolean = oldItem == newItem
    }
}