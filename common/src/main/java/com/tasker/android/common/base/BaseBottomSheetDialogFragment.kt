package com.tasker.android.common.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tasker.android.common.R

abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int,
) : BottomSheetDialogFragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        connectViewModel()
        init()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.transparent_dialog)
        dialog.apply {
            setCanceledOnTouchOutside(true)
            behavior.isDraggable = false
            window?.attributes?.windowAnimations = R.style.animation_slide_up
        }
        return dialog
    }

    abstract fun connectViewModel()
    abstract fun init()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}