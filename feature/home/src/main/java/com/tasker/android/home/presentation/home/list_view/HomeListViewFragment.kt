package com.tasker.android.home.presentation.home.list_view

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.common.util.clearKeyboardFocus
import com.tasker.android.common.util.requestKeyboardFocus
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeListViewBinding
import com.tasker.android.home.presentation.home.main.HomeFragmentDirections

class HomeListViewFragment :
    BaseFragment<FragmentHomeListViewBinding>(R.layout.fragment_home_list_view) {

    private val viewModel: HomeListViewModel by viewModels()
    private val homeListViewAdapter by lazy {
        HomeListViewAdapter { index ->
            navigateToDetailPage(
                index
            )
        }
    }

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
        convertEditMode(false)
        initListView()
        initComponentFunction()
    }

    private fun initListView() {
        binding.rvHomeListView.adapter = homeListViewAdapter
        binding.rvHomeListView.addItemDecoration(HomeListViewItemDecoration(requireContext()))

        viewModel.setTaskListDummyData()
        homeListViewAdapter.submitList(viewModel.taskList.value)
    }

    private fun initComponentFunction() {
        binding.llHomeListViewAdd.setOnClickListener {
            convertEditMode(true)
        }
    }

    private fun convertEditMode(editMode: Boolean) {
        binding.apply {
            when (editMode) {
                true -> {
                    llHomeListViewAdd.visibility = View.GONE
                    clHomeTaskAdd.visibility = View.VISIBLE
                    etHomeTaskAdd.requestFocus()
                    requestKeyboardFocus(requireActivity(), etHomeTaskAdd)
                }

                false -> {
                    llHomeListViewAdd.visibility = View.VISIBLE
                    clHomeTaskAdd.visibility = View.GONE
                    etHomeTaskAdd.clearFocus()
                    etHomeTaskAdd.text?.clear()
                    clearKeyboardFocus(requireActivity(), etHomeTaskAdd)
                }
            }
        }
    }

    private fun navigateToDetailPage(listIndex: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToHomeTaskDetailFragment(
                viewModel.taskList.value[listIndex]
            )
        )
    }
}