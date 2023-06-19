package com.tasker.android.home.presentation.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.presentation.model.HomeTaskData
import com.tasker.android.home.presentation.util.HomeTaskDesignHelper

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

                val homeTaskDesignHelper = HomeTaskDesignHelper(context, binding = binding)
                homeTaskDesignHelper.applyDesign(data = data)
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