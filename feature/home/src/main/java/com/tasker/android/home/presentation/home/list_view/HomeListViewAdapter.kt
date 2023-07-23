package com.tasker.android.home.presentation.home.list_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.model.room.LocalTask
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.util.HomeTaskDesignHelper

class HomeListViewAdapter(
    private val navigateToDetailPage: (Int) -> Unit,
) :
    ListAdapter<LocalTask, HomeListViewAdapter.HomeListViewHolder>(HomeListViewDiffUtil()) {

    private lateinit var binding: ItemHomeListViewBinding
    private lateinit var homeTaskDesignHelper: HomeTaskDesignHelper

    private class HomeListViewDiffUtil : DiffUtil.ItemCallback<LocalTask>() {
        override fun areItemsTheSame(
            oldItem: LocalTask, newItem: LocalTask,
        ): Boolean = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: LocalTask, newItem: LocalTask,
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder {
        binding =
            ItemHomeListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        homeTaskDesignHelper = HomeTaskDesignHelper(binding)
        return HomeListViewHolder()
    }


    override fun onBindViewHolder(holder: HomeListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomeListViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LocalTask) {
            binding.apply {
                localTaskData = data
                homeTaskDesignHelper.applyDesign(data)

                clHomeTask.setOnClickListener {
                    navigateToDetailPage(adapterPosition)
                }
            }
        }
    }
}