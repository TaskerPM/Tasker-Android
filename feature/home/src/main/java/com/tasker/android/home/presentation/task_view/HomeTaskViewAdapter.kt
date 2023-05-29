package com.tasker.android.home.presentation.task_view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tasker.android.home.presentation.task_view.list.HomeListViewFragment

class HomeTaskViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragments = listOf(
            HomeListViewFragment(),
            HomeTimeTypeFragment(),
            HomeCategoryTypeFragment()
        )

        return fragments[position]
    }
}