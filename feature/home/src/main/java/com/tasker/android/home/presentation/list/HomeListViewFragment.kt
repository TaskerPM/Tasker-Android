package com.tasker.android.home.presentation.list

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.common.util.clearKeyboardFocus
import com.tasker.android.common.util.requestKeyboardFocus
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeListViewBinding
import com.tasker.android.home.presentation.main.HomeFragmentDirections
import com.tasker.android.home.presentation.main.HomeViewModel
import com.tasker.android.home.util.SwipeController
import kotlinx.coroutines.launch


class HomeListViewFragment :
    BaseFragment<FragmentHomeListViewBinding>(R.layout.fragment_home_list_view) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: HomeListViewModel by viewModels()

    private val homeListViewAdapter by lazy {
        HomeListViewAdapter { index -> navigateToDetailPage(index) }
    }

    override fun connectViewModel() {
        binding.homeViewModel = homeViewModel
    }

    override fun init() {
        convertEditMode(false)
        initListView()
        initComponentFunction()
    }

    private fun initListView() {
        val swipeItemHelper = SwipeController(requireContext()) { position -> deleteTask(position) }
        val itemTouchHelper = ItemTouchHelper(swipeItemHelper)

        binding.rvHomeListView.apply {
            this.adapter = homeListViewAdapter
            this.addItemDecoration(HomeListViewItemDecoration(requireContext()))
            itemTouchHelper.attachToRecyclerView(this)
        }

        viewModel.initTaskList(homeViewModel.selectedDate.value, homeViewModel.taskList.value)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taskList.collect {
                    homeListViewAdapter.submitList(it)
                    Log.d("list", it.toString())
                    convertEditMode(false)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.selectedDate.collect {
                    viewModel.initTaskList(it, homeViewModel.taskList.value)
                }
            }
        }
    }

    private fun initComponentFunction() {
        binding.apply {
            llHomeTaskAddBtn.setOnClickListener {
                convertEditMode(true)
            }

            clHomeTaskInput.setOnClickListener(null)

            root.setOnClickListener { view ->
                if (view.id != R.id.cl_home_task_input) {
                    convertEditMode(editMode = false, saveStatus = true)
                }
            }
        }
    }

    private fun convertEditMode(editMode: Boolean, saveStatus: Boolean = false) {
        binding.apply {
            when (editMode) {
                true -> {
                    llHomeTaskAddBtn.visibility = View.GONE
                    clHomeTaskInput.visibility = View.VISIBLE
                    etHomeTaskAdd.requestFocus()
                    requestKeyboardFocus(etHomeTaskAdd)
                }

                false -> {
                    if (saveStatus) {
                        addNewTask(binding.etHomeTaskAdd.text.toString().trim())
                    }
                    llHomeTaskAddBtn.visibility = View.VISIBLE
                    clHomeTaskInput.visibility = View.GONE
                    etHomeTaskAdd.clearFocus()
                    etHomeTaskAdd.text?.clear()
                    clearKeyboardFocus(etHomeTaskAdd)
                }
            }
        }
    }

    private fun addNewTask(text: String) {
        if (text.isNotBlank()) {
            viewModel.addTask(text)
        }
    }

    private fun navigateToDetailPage(listIndex: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToHomeDetailPageFragment(
                homeViewModel.taskList.value[listIndex]
            )
        )
    }

    private fun deleteTask(position: Int) {
        viewModel.removeTask(position)
    }
}