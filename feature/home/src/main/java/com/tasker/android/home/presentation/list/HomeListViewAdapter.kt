package com.tasker.android.home.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.databinding.ItemHomeListViewBinding
import com.tasker.android.home.model.HomeTaskData
import com.tasker.android.home.util.HomeTaskDesignHelper

class HomeListViewAdapter(
    private val navigateToDetailPage: (Int) -> Unit,
) :
    ListAdapter<HomeTaskData, HomeListViewAdapter.HomeListViewHolder>(HomeListViewDiffUtil()) {

    private lateinit var binding: ItemHomeListViewBinding
    private lateinit var homeTaskDesignHelper: HomeTaskDesignHelper

    private class HomeListViewDiffUtil : DiffUtil.ItemCallback<HomeTaskData>() {
        override fun areItemsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
        ): Boolean = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: HomeTaskData, newItem: HomeTaskData,
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

        fun bind(data: HomeTaskData) {
            binding.apply {
                tvHomeTaskText.text = data.text

                homeTaskDesignHelper.applyDesign(data)

                clHomeTask.setOnClickListener {
                    navigateToDetailPage(adapterPosition)
                }
            }
        }
    }
}