package com.tasker.android.home.util

import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.getDrawableFrom
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.model.HomeTaskData
import android.content.Context
import android.view.View
import com.tasker.android.common.util.applyDrawableTintColor
import com.tasker.android.home.R

class HomeTaskDesignHelper(
    private val context: Context,
    private val binding: ItemHomeListViewBinding,
) {

    fun applyDesign(data: HomeTaskData) {
        binding.apply {
            tvHomeTaskText.text = data.text

            applyCompletedDesign(isCompleted = data.isCompleted)
            applyCategoryTagDesign(isCompleted = data.isCompleted, data = data)
            applyTimeTagDesign(isCompleted = data.isCompleted, data = data)
            applyFilledDesign(data.isFilled)
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
                        this.text = data.categoryTag
                        applyDrawableTintColor(
                            context,
                            this.background,
                            com.tasker.android.common.R.color.gray_30
                        )
                        this.setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.gray_230
                            )
                        )
                    }
                    false -> {
                        this.text = data.categoryTag
                        applyDrawableTintColor(
                            context,
                            this.background,
                            com.tasker.android.common.R.color.temp_tag_category
                        )
                        this.setTextColor(
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
                        this.text = data.timeTag
                        applyDrawableTintColor(
                            context,
                            this.background,
                            com.tasker.android.common.R.color.gray_30
                        )
                        this.setTextColor(
                            getColorFrom(
                                context,
                                com.tasker.android.common.R.color.gray_230
                            )
                        )
                    }
                    false -> {
                        this.text = data.timeTag
                        applyDrawableTintColor(
                            context,
                            this.background,
                            com.tasker.android.common.R.color.temp_tag_time
                        )
                        this.setTextColor(
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