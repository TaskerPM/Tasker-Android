package com.tasker.android.home.presentation.home

import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeBinding
import com.tasker.android.home.presentation.model.DayNDate
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvHomeDatePicker.apply {
            adapter = HomeDatePickerAdapter(
                list = listOf(
                    DayNDate("월", 30),
                    DayNDate("화", 31),
                    DayNDate("수", 1),
                    DayNDate("목", 2),
                    DayNDate("금", 3),
                    DayNDate("토", 4),
                    DayNDate("일", 5),
                )
            )

            addItemDecoration(HomeDatePickerItemDecoration(requireContext()))
        }
    }
}