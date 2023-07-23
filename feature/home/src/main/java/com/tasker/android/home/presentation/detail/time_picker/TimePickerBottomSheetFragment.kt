package com.tasker.android.home.presentation.detail.time_picker

import android.util.Log
import com.tasker.android.common.base.BaseBottomSheetDialogFragment
import com.tasker.android.home.R
import com.tasker.android.home.databinding.BottomSheetTimePickerBinding
import com.tasker.android.common.dialog.ConfirmationDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimePickerBottomSheetFragment : BaseBottomSheetDialogFragment<BottomSheetTimePickerBinding>(
    R.layout.bottom_sheet_time_picker
) {
    private var startTime = ""
    private var endTime = ""

    override fun connectViewModel() {
    }

    private var onConfirmClickListener: (() -> Unit)? = null

    override fun init() {
        setInitialTime()
        initListener()
    }

    private fun initListener() {
        binding.tvTimepickerNext.setOnClickListener {
            if (binding.tvTimepickerTitle.text == getString(R.string.timepicker_bottom_sheet_start_time)) {
                binding.tvTimepickerTitle.text = getString(R.string.timepicker_bottom_sheet_end_time)
                binding.tvTimepickerNext.text = getString(R.string.timepicker_bottom_sheet_confirm)
                startTime = getTimeFromTimePicker()
                Log.d("시작시간", startTime)
            } else {
                endTime = getTimeFromTimePicker()
                Log.d("종료시간", endTime)
                if (startTime == endTime) {
                    showInvalidTimeIntervalDialog(getString(R.string.confirmation_dialog_same_start_end_title))
                } else if (isEndTimeBeforeStartTime(startTime, endTime)) {
                    showInvalidTimeIntervalDialog(getString(R.string.confirmation_dialog_end_before_start_title))
                } else {
                    // 뷰모델에 있는 변수에 저장
                    dismiss()
                }
            }
        }
    }

    fun setOnConfirmClickListener(listener: () -> Unit) {
        onConfirmClickListener = listener
    }

    private fun setInitialTime() {
        val calendar = Calendar.getInstance()
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMinute = calendar.get(Calendar.MINUTE)

        binding.apply {
            timepicker.hour = curHour
            timepicker.minute = curMinute
        }
    }

    private fun getTimeFromTimePicker(): String {
        val hour = binding.timepicker.hour
        val minute = binding.timepicker.minute
        return String.format("%02d:%02d", hour, minute)
    }

    private fun isEndTimeBeforeStartTime(startTime: String, endTime: String): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val startTimeObj = sdf.parse(startTime)
        val endTimeObj = sdf.parse(endTime)
        return endTimeObj.before(startTimeObj)
    }

    private fun showInvalidTimeIntervalDialog(title: String) {
        val dialog = ConfirmationDialog(title)
        dialog.setButtonClickListener(object : ConfirmationDialog.OnButtonClickListener {
            override fun onConfirmBtnClicked() {
                startTime = ""
                endTime = ""
                dismiss()
            }
        })
        dialog.show(childFragmentManager, "UnsavedChangesDialog")
    }
}