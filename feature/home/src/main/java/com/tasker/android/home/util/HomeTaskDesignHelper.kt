package com.tasker.android.home.util

import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.model.HomeTaskData
import android.content.Context
import android.view.View
import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.common.util.applyDrawableTintColor
import com.tasker.android.home.R

class HomeTaskDesignHelper(
    private val binding: ItemHomeListViewBinding,
) {

    lateinit var context: Context

    fun applyDesign(data: LocalTask) {
        context = binding.root.context
        binding.apply {
            tvHomeTaskText.text = data.title

//            applyCompletedDesign(isCompleted = data.isCompleted)
//            applyCategoryTagDesign(isCompleted = data.isCompleted, data = data)
//            applyTimeTagDesign(isCompleted = data.isCompleted, data = data)
//            applyFilledDesign(data.isFilled)
        }
    }

    private fun applyCompletedDesign(isCompleted: Boolean) {
        binding.apply {
            when (isCompleted) {
                true -> {
                    ivHomeTaskIsCompleted.setImageResource(R.drawable.icon_home_task_completed)
                    vHomeTaskIsCompleted.visibility = View.VISIBLE
                    tvHomeTaskText.setTextColor(
                        getColorFrom(
                            context,
                            com.tasker.android.common.R.color.gray_230
                        )
                    )
                }
                false -> {
                    ivHomeTaskIsCompleted.setImageResource(R.drawable.icon_home_task_uncompleted)
                    vHomeTaskIsCompleted.visibility = View.GONE
                    tvHomeTaskText.setTextColor(
                        getColorFrom(
                            context,
                            com.tasker.android.common.R.color.black_basic
                        )
                    )
                }
            }
        }
    }

    private fun applyCategoryTagDesign(isCompleted: Boolean, data: HomeTaskData) {
        binding.tvHomeTaskTagCategory.apply {
            if (data.categoryTag.isNotEmpty()) {
                when (isCompleted) {
                    true -> {
                        text = data.categoryTag
                        applyDrawableTintColor(
                            context,
                            background,
                            com.tasker.android.common.R.color.gray_30
                        )
                        setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.gray_230
                            )
                        )
                    }
                    false -> {
                        text = data.categoryTag
                        applyDrawableTintColor(
                            context,
                            background,
                            com.tasker.android.common.R.color.temp_tag_category
                        )
                        setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.temp_tag_text_category
                            )
                        )
                    }
                }
            }
        }
    }

    private fun applyTimeTagDesign(isCompleted: Boolean, data: HomeTaskData) {
        binding.tvHomeTaskTagTime.apply {
            if (data.timeTag.isNotEmpty()) {
                when (isCompleted) {
                    true -> {
                        text = data.timeTag
                        applyDrawableTintColor(
                            context,
                            background,
                            com.tasker.android.common.R.color.gray_30
                        )
                        setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.gray_230
                            )
                        )
                    }
                    false -> {
                        text = data.timeTag
                        applyDrawableTintColor(
                            context,
                            background,
                            com.tasker.android.common.R.color.temp_tag_time
                        )
                        setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.temp_tag_text_time
                            )
                        )
                    }
                }

            }
        }
    }

    private fun applyFilledDesign(isFilled: Boolean) {
        when (isFilled) {
            true -> {
                binding.clHomeTask.background =
                    getDrawableFrom(
                        context,
                        R.drawable.background_item_home_task_filled
                    )
            }
            false -> {
                binding.clHomeTask.background =
                    getDrawableFrom(
                        context,
                        R.drawable.background_item_home_task_unfilled
                    )
            }
        }
    }


}