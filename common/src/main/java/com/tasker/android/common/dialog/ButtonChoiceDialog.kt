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
import com.tasker.android.common.databinding.DialogButtonChoiceBinding

class ButtonChoiceDialog(
    private val title: String,
    private val subtitle: String,
    private val leftBtn: String,
    private val rightBtn: String,
) : DialogFragment() {

    private lateinit var binding: DialogButtonChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_button_choice,
            container,
            false
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        initDialog()
        setDialogContent()
        return binding.root
    }

    fun setDialogContent() {
        binding.tvButtonChoiceDialogTitle.text = title
        binding.tvButtonChoiceDialogSubtitle.text = subtitle
        binding.tvButtonChoiceDialogLeftBtn.text = leftBtn
        binding.tvButtonChoiceDialogRightBtn.text = rightBtn
    }

    fun initDialog() {
        binding.tvButtonChoiceDialogLeftBtn.setOnClickListener {
            buttonClickListener.onLeftBtnClicked()
            dismiss()
        }

        binding.tvButtonChoiceDialogRightBtn.setOnClickListener {
            buttonClickListener.onRightBtnClicked()
            dismiss()
        }
    }

    interface OnButtonClickListener {
        fun onLeftBtnClicked()
        fun onRightBtnClicked()
    }

    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }

    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener

}