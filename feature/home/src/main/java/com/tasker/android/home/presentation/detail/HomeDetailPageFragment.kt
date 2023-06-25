package com.tasker.android.home.presentation.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasker.android.common.adapter.NoteAdapter
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.common.util.applyDrawableTintColor
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.requestKeyboardFocus
import com.tasker.android.home.R
import com.tasker.android.home.databinding.FragmentHomeDetailPageBinding
import com.tasker.android.home.model.HomeTaskData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeDetailPageFragment :
    BaseFragment<FragmentHomeDetailPageBinding>(R.layout.fragment_home_detail_page) {

    private val viewModel: HomeDetailPageViewModel by viewModels()
    private val navArgs: HomeDetailPageFragmentArgs by navArgs()

    private val noteAdapter by lazy { NoteAdapter() }

    private var isFirstAddedNote = true

    override fun connectViewModel() {
        binding.viewModel = viewModel
    }

    override fun init() {
        initBackNavigation()
        initMenu()
        initComponentFunction()
        initTaskData()
        initNoteView()
    }

    private fun initMenu() {
        binding.tbHomeDetailPage.tvBtnRight.visibility = View.VISIBLE
        binding.tbHomeDetailPage.tvBtnRight.text = getString(R.string.save)
    }

    private fun initBackNavigation() {
        binding.tbHomeDetailPage.tbBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initComponentFunction() {
        binding.clHomeDetailPageAddNote.setOnClickListener {
            setEditableNote(isEnabled = true)
            if (isFirstAddedNote) {
                isFirstAddedNote = false
            } else {
                viewModel.addNotes(binding.itemHomeDetailPageNote.etNoteContent.text.toString())
                binding.itemHomeDetailPageNote.etNoteContent.text = null
            }

        }
    }

    private fun setEditableNote(isEnabled: Boolean) {
        binding.apply {
            itemHomeDetailPageNote.tvNoteContent.visibility = View.GONE
            itemHomeDetailPageNote.etNoteContent.visibility = View.VISIBLE
            when (isEnabled) {
                true -> {
                    itemHomeDetailPageNote.root.visibility = View.VISIBLE
                    itemHomeDetailPageNote.etNoteContent.requestFocus()
                    requestKeyboardFocus(requireActivity(), itemHomeDetailPageNote.etNoteContent)
                }

                false -> {

                }
            }
        }
    }

    private fun initNoteView() {
        viewModel.getDummyNotes()

        binding.rvHomeDetailPageNote.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initTaskData() {
        viewModel.selectTask(navArgs.taskData)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(viewModel.selectedTaskData, viewModel.noteList) { task, note ->
                    initCategoryTag(task)
                    initTimeTag(task)
                    initNoteContent(note)
                }.collect()
            }
        }
    }

    private fun initCategoryTag(task: HomeTaskData) {
        binding.tvHomeDetailPageCategoryTag.apply {
            if (task.categoryTag.isEmpty()) {

                this.text = getString(R.string.home_detail_page_tag_none)

                this.setTextColor(
                    getColorFrom(
                        requireContext(),
                        com.tasker.android.common.R.color.white
                    )
                )

                applyDrawableTintColor(
                    requireContext(),
                    this.background,
                    com.tasker.android.common.R.color.gray_300
                )
            } else {

                this.text = task.categoryTag

                this.setTextColor(
                    getColorFrom(
                        requireContext(),
                        com.tasker.android.common.R.color.temp_tag_text_category
                    )
                )

                applyDrawableTintColor(
                    requireContext(),
                    this.background,
                    com.tasker.android.common.R.color.temp_tag_category
                )
            }
        }
    }

    private fun initTimeTag(task: HomeTaskData) {
        binding.tvHomeDetailPageTimeTag.apply {
            if (task.categoryTag.isEmpty()) {

                this.text = getString(R.string.home_detail_page_tag_none)

                this.setTextColor(
                    getColorFrom(
                        requireContext(),
                        com.tasker.android.common.R.color.white
                    )
                )

                applyDrawableTintColor(
                    requireContext(),
                    this.background,
                    com.tasker.android.common.R.color.gray_300
                )
            } else {

                this.text = task.timeTag

                this.setTextColor(
                    getColorFrom(
                        requireContext(),
                        com.tasker.android.common.R.color.temp_tag_text_time
                    )
                )

                applyDrawableTintColor(
                    requireContext(),
                    this.background,
                    com.tasker.android.common.R.color.temp_tag_time
                )
            }
        }
    }

    private fun initNoteContent(note: List<String>) {
        (binding.rvHomeDetailPageNote.adapter as NoteAdapter).differ.submitList(note)
    }
}