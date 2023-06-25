package com.tasker.android.home.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.DialogUnsavedChangesBinding

class UnsavedChangesDialog : DialogFragment() {

    private lateinit var binding: DialogUnsavedChangesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_unsaved_changes,
            container,
            false
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        initDialog()
        return binding.root
    }

    fun initDialog() {
        binding.tvDialogSave.setOnClickListener {
            buttonClickListener.onSaveBtnClicked()
            dismiss()
        }

        binding.tvDialogBack.setOnClickListener {
            buttonClickListener.onBackBtnClicked()
            dismiss()
        }
    }

    interface OnButtonClickListener {
        fun onSaveBtnClicked()
        fun onBackBtnClicked()
    }

    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener

}