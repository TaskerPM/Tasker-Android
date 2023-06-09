package com.tasker.android.home.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tasker.android.home.presentation.time.HomeTimeViewFragment
import com.tasker.android.home.presentation.category.HomeCategoryViewFragment
import com.tasker.android.home.presentation.list.HomeListViewFragment

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