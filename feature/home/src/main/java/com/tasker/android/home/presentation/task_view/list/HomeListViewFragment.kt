package com.tasker.android.home.presentation.task_view.list

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeListViewBinding
import com.tasker.android.home.presentation.model.HomeListViewTaskData

class HomeListViewFragment :
    BaseFragment<FragmentHomeListViewBinding>(R.layout.fragment_home_list_view) {

    private val homeListViewAdapter by lazy { HomeListViewAdapter() }

    val list = mutableListOf<HomeListViewTaskData>()

    override fun connectViewModel() {

    }

    override fun init() {
        convertEditMode(false)
        initListView()
        initComponentFunction()
    }

    private fun initListView() {
        binding.rvHomeListView.adapter = homeListViewAdapter
        binding.rvHomeListView.addItemDecoration(HomeListViewItemDecoration(requireContext()))

        //set dummy data
        list.add(HomeListViewTaskData("IA 구조도 그리기", false, true, "스터디", "14:00-17:00"))
        list.add(HomeListViewTaskData("와이어프레임 제작-lofi", false, false, "", ""))
        list.add(HomeListViewTaskData("IA 구조도 그리기", true, true, "스터디", "14:00-17:00"))
        list.add(HomeListViewTaskData("와이어프레임 제작-lofi", true, false, "", ""))

        lifecycleScope.launchWhenStarted {
            homeListViewAdapter.submitList(list)
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