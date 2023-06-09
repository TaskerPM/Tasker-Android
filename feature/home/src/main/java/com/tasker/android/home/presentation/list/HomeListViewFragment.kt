package com.tasker.android.home.presentation.list

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeListViewBinding

class HomeListViewFragment :
    BaseFragment<FragmentHomeListViewBinding>(R.layout.fragment_home_list_view) {

    private val viewModel: HomeListViewModel by viewModels()
    private val homeListViewAdapter by lazy { HomeListViewAdapter() }

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

        lifecycleScope.launchWhenStarted {
            homeListViewAdapter.submitList(viewModel.taskList.value)
        }
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
                    showKeyboard(etHomeTaskAdd)
                }

                false -> {
                    llHomeListViewAdd.visibility = View.VISIBLE
                    clHomeTaskAdd.visibility = View.GONE
                    etHomeTaskAdd.clearFocus()
                    etHomeTaskAdd.text?.clear()
                    hideKeyboard(binding.etHomeTaskAdd)
                }
            }
        }
    }

    private fun showKeyboard(editText: EditText) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }

    private fun hideKeyboard(editText: EditText) {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}