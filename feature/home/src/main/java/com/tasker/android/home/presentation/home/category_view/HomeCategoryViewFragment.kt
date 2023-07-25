package com.tasker.android.home.presentation.home.category_view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeCategoryViewBinding
import com.tasker.android.home.presentation.home.main.HomeViewModel
import com.tasker.android.home.util.SwipeController
import kotlinx.coroutines.launch

class HomeCategoryViewFragment :
    BaseFragment<FragmentHomeCategoryViewBinding>(R.layout.fragment_home_category_view) {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: HomeCategoryViewModel by viewModels()

    private val homeCategoryViewAdapter by lazy {
        HomeCategoryViewAdapter(requireActivity().window.decorView.rootView)
        { index -> navigateToDetailPage(index) }
    }

    override fun connectViewModel() {}

    override fun init() {
        initCategoryView()
    }

    private fun initCategoryView() {
        val swipeItemHelper = SwipeController(
            context = requireContext(),
            onSwiped = { position -> deleteTask(position) },
        )

        binding.rvHomeCategoryView.apply {
            this.adapter = homeCategoryViewAdapter
            this.addItemDecoration(HomeCategoryViewItemDecoration(requireContext()))
            ItemTouchHelper(swipeItemHelper).attachToRecyclerView(this)
        }

        viewModel.initTaskList(homeViewModel.selectedDate.value, homeViewModel.taskList.value)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taskList.collect {
                    homeCategoryViewAdapter.submitList(it)
                }
            }
        }
    }

    private fun navigateToDetailPage(listIndex: Int) {
//        findNavController().navigate(
//            HomeFragmentDirections.actionHomeFragmentToHomeDetailPageFragment(
//                homeViewModel.taskList.value[listIndex]
//            )
//        )
    }

    private fun deleteTask(position: Int) {
        viewModel.removeTask(position)
    }

}