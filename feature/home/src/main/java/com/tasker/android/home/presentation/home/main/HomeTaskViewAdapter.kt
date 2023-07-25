package com.tasker.android.home.presentation.home.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tasker.android.home.presentation.home.time_view.HomeTimeViewFragment
import com.tasker.android.home.presentation.home.category_view.HomeCategoryViewFragment
import com.tasker.android.home.presentation.home.list_view.HomeListViewFragment

class HomeTaskViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragments = listOf(
            HomeListViewFragment(),
            HomeTimeViewFragment(),
            HomeCategoryViewFragment()
        )

        return fragments[position]
    }
}