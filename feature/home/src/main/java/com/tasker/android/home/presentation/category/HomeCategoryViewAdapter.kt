package com.tasker.android.home.presentation.category

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.*
import com.tasker.android.home.databinding.ItemHomeAddTaskBinding
import com.tasker.android.home.databinding.ItemHomeCategoryHeaderBinding
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.model.HomeTaskData
import com.tasker.android.home.util.HomeTaskDesignHelper

class HomeCategoryViewAdapter(
    private val fragmentRootView: View,
    private val navigateToDetailPage: (Int) -> Unit,
) :
    ListAdapter<HomeTaskData, RecyclerView.ViewHolder>(HomeListViewDiffUtil()) {

    private lateinit var context: Context
    private lateinit var homeTaskDesignHelper: HomeTaskDesignHelper

    private class HomeListViewDiffUtil : DiffUtil.ItemCallback<HomeTaskData>() {
        override fun areItemsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
        ): Boolean = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
        ): Boolean = oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int {
        val divider = currentList[position]

        return if (divider.text == "HEADER" && divider.day == 0) {
            CATEGORY_HEADER
        } else if (divider.text == "ADD" && divider.day == 0) {
            CATEGORY_EDITTEXT_ADD
        } else {
            CATEGORY_ITEM
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val listViewBinding = ItemHomeListViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        homeTaskDesignHelper = HomeTaskDesignHelper(listViewBinding)

        return when (viewType) {
            CATEGORY_HEADER -> {
                HomeCategoryHeaderHolder(
                    ItemHomeCategoryHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            CATEGORY_ITEM -> {
                HomeCategoryViewHolder(listViewBinding)
            }

            CATEGORY_EDITTEXT_ADD -> {
                HomeCategoryAddViewHolder(
                    ItemHomeAddTaskBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                throw java.lang.ClassCastException("Unknown ViewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryHeaderHolder -> {
                holder.bind(currentList[position])
            }

            is HomeCategoryViewHolder -> {
                holder.bind(currentList[position])
            }

            is HomeCategoryAddViewHolder -> {
                holder.bind()
            }
        }
    }

    inner class HomeCategoryHeaderHolder(private val binding: ItemHomeCategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var data: HomeTaskData

        fun bind(data: HomeTaskData) {
            this.data = data

            initHeaderData()
            initComponentFunction()
        }

        private fun initHeaderData() {
            binding.apply {
                tvHomeCategoryHeader.apply {
                    if (data.categoryTag.isBlank()) {
                        visibility = View.GONE
                        tvHomeCategoryHeaderNone.visibility = View.VISIBLE
                    } else {
                        text = data.categoryTag
                        applyDrawableTintColor(
                            context,
                            this.background,
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

        private fun initComponentFunction() {
            binding.tvHomeCategoryAdd.setOnClickListener {
                val addHeader = currentList.indexOfFirst { it.text == "ADD" }

                if (addHeader == -1) {
                    insertAddTask()
                } else {
                    removeAddTask()
                }
            }
        }

        private fun insertAddTask() {
            val nextHeaderIndex = currentList.indexOfFirst {
                it.text == "HEADER" && it.day == 0 && currentList.indexOf(it) > adapterPosition
            }

            val newList = currentList.toMutableList()

            if (nextHeaderIndex == -1) {
                val newItem = HomeTaskData("ADD", day = 0, categoryTag = "")
                newList.add(newItem)
            } else {
                val newItem = HomeTaskData("ADD", day = 0, categoryTag = data.categoryTag)
                newList.add(nextHeaderIndex, newItem)
            }

            submitList(newList)
        }
    }

    inner class HomeCategoryViewHolder(private val binding: ItemHomeListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HomeTaskData) {
            binding.apply {
                taskData = data
                homeTaskDesignHelper.applyDesign(data)

                clHomeTask.setOnClickListener {
                    navigateToDetailPage(adapterPosition)
                }

                binding.tvHomeTaskTagCategory.visibility = View.GONE
            }
        }
    }

    inner class HomeCategoryAddViewHolder(private val binding: ItemHomeAddTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                root.post {
                    etHomeTaskAdd.apply {
                        requestFocus()
                        requestKeyboardFocus(this)

                        setupRecyclerViewTouchListener()
                    }
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setupRecyclerViewTouchListener() {
            val onTouchOutListener = getTouchOutListener(binding.etHomeTaskAdd) {
                removeAddTask(binding.etHomeTaskAdd.text.toString(), adapterPosition)
            }

            (itemView.parent as RecyclerView).setOnTouchListener(onTouchOutListener)
            fragmentRootView.setOnTouchListener(onTouchOutListener)
        }
    }

    private fun removeAddTask(text: String = "", index: Int = -1) {
        val listWithoutAdd = currentList.filter { it.text != "ADD" }.toMutableList()

        if (text.isNotBlank() && index != -1) {
            listWithoutAdd.add(index, HomeTaskData(text = text))
        }

        submitList(listWithoutAdd)

        // save list in view model
    }
}


private const val CATEGORY_ITEM = 0
private const val CATEGORY_HEADER = 10
private const val CATEGORY_EDITTEXT_ADD = 20