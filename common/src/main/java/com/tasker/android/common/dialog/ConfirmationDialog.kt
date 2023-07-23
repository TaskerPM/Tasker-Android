package com.tasker.android.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tasker.android.common.R
import com.tasker.android.common.databinding.DialogConfirmationBinding

class ConfirmationDialog(private val title: String) : DialogFragment() {

    private lateinit var binding: DialogConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_confirmation,
            container,
            false
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        initDialog()
        setDialogTitle()
        return binding.root
    }

    fun setDialogTitle() {
        binding.tvConfirmationDialogTitle.text = title
    }

    fun initDialog() {
        binding.tvConfirmationDialogConfirm.setOnClickListener {
            buttonClickListener.onConfirmBtnClicked()
            dismiss()
        }
    }

    interface OnButtonClickListener {
        fun onConfirmBtnClicked()
    }

    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }


    private lateinit var buttonClickListener: OnButtonClickListener

}