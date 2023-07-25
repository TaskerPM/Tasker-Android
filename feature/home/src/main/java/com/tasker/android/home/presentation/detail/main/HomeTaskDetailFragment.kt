package com.tasker.android.home.presentation.detail.main

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasker.android.common.adapter.NoteAdapter
import com.tasker.android.common.adapter.SwipeController
import com.tasker.android.common.base.BaseFragment
import com.tasker.android.common.util.applyDrawableTintColor
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.common.util.requestKeyboardFocus
import com.tasker.android.home.R
import com.tasker.android.home.model.HomeTaskData
import com.tasker.android.common.dialog.ButtonChoiceDialog
import com.tasker.android.home.databinding.FragmentHomeTaskDetailBinding
import com.tasker.android.home.presentation.detail.time_picker.TimePickerBottomSheetFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeTaskDetailFragment :
    BaseFragment<FragmentHomeTaskDetailBinding>(R.layout.fragment_home_task_detail) {

    private val viewModel: HomeTaskDetailViewModel by viewModels()
    private val navArgs: HomeTaskDetailFragmentArgs by navArgs()

    private val noteAdapter by lazy { NoteAdapter() }

    private var isFirstAddedNote = true

    private lateinit var timeTickerBottomSheet: TimePickerBottomSheetFragment

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
            if (!isFirstAddedNote) {
                showUnSavedChangesDialog()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    private fun initComponentFunction() {
        binding.clHomeTaskDetailAddNote.setOnClickListener {
            setEditableNote(isEnabled = true, isFocus = true)
            isFirstAddedNote = false
            viewModel.addNote(binding.itemHomeTaskDetailNote.etNoteContent.text.toString())
            binding.itemHomeTaskDetailNote.etNoteContent.text = null
        }

        binding.tvHomeTaskDetailCategoryTag.setOnClickListener {
            findNavController().navigate(HomeTaskDetailFragmentDirections.actionHomeDetailPageFragmentToHomeCategoryFragment())
        }

        binding.tvHomeTaskDetailTimeTag.setOnClickListener {
            timeTickerBottomSheet = TimePickerBottomSheetFragment()
            timeTickerBottomSheet.setOnConfirmClickListener {
                timeTickerBottomSheet.dismiss()
            }
            timeTickerBottomSheet.show(childFragmentManager, "timeTicker")
        }
    }

    private fun setEditableNote(isEnabled: Boolean, isFocus: Boolean) {
        binding.apply {
            itemHomeTaskDetailNote.tvNoteContent.visibility = View.GONE
            itemHomeTaskDetailNote.etNoteContent.visibility = View.VISIBLE
            when (isEnabled) {
                true -> {
                    itemHomeTaskDetailNote.root.visibility = View.VISIBLE
                    itemHomeTaskDetailNote.etNoteContent.requestFocus()
                    requestKeyboardFocus(itemHomeTaskDetailNote.etNoteContent)
                    if (isFocus) {
                        requestKeyboardFocus(
                            itemHomeTaskDetailNote.etNoteContent
                        )
                    }
                }

                false -> {

                }
            }
        }
    }

    private fun initNoteView() {
        viewModel.getDummyNotes()

        binding.rvHomeTaskDetailNote.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // 노트가 없을 때
//        lifecycleScope.launch {
//            viewModel.noteList.collect { noteList ->
//                Log.d("노트", "$noteList")
//                if (noteList.isEmpty()) {
//                    setEditableNote(isEnabled = true, isFocus = false)
//                }
//            }
//        }
    }

    private fun initTaskData() {
//        viewModel.selectTask(navArgs.taskData)

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
        binding.tvHomeTaskDetailCategoryTag.apply {
            if (task.categoryTag.isEmpty()) {

                this.text = getString(R.string.home_task_detail_tag_none)

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
        binding.tvHomeTaskDetailTimeTag.apply {
            if (task.categoryTag.isEmpty()) {

                this.text = getString(R.string.home_task_detail_tag_none)

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

                // 여기서 시간차이 발생하면 1시간 간격마다 컬러 주입

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
        val swipeItemHelper = SwipeController(
            requireContext()
        ) { position -> deleteNote(position) }
        val itemTouchHelper = ItemTouchHelper(swipeItemHelper)

        binding.rvHomeTaskDetailNote.apply {
            this.adapter = noteAdapter
            itemTouchHelper.attachToRecyclerView(this)
        }

        (binding.rvHomeTaskDetailNote.adapter as NoteAdapter).differ.submitList(note)
    }

    private fun showUnSavedChangesDialog() {
        val dialog = ButtonChoiceDialog(
            title = getString(R.string.button_choice_dialog_title),
            subtitle = getString(R.string.button_choice_dialog_subtitle),
            leftBtn = getString(R.string.button_choice_dialog_save),
            rightBtn = getString(R.string.button_choice_dialog_go_back)
        )

        dialog.setButtonClickListener(object : ButtonChoiceDialog.OnButtonClickListener {
            override fun onLeftBtnClicked() {
            }

            override fun onRightBtnClicked() {
                findNavController().navigateUp()
            }
        })
        dialog.show(childFragmentManager, "UnsavedChangesDialog")
    }

    private fun deleteNote(position: Int) {
        viewModel.removeNote(position)
    }
}