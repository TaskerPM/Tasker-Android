package com.tasker.android.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

fun getDrawableFrom(context: Context, @DrawableRes Id: Int) =
    ResourcesCompat.getDrawable(context.resources, Id, null)

fun getColorFrom(context: Context, @ColorRes Id: Int) =
    context.resources.getColor(Id, null)

fun applyDrawableTintColor(context: Context, drawable: Drawable, @ColorRes Id: Int) {
    val wrappedDrawable = DrawableCompat.wrap(drawable)
    DrawableCompat.setTint(wrappedDrawable, getColorFrom(context, Id))
}

fun getDayOfWeekValue(year: Int, month: Int, day: Int): Int {
    return LocalDate.of(year, month, day).dayOfWeek.value
}

fun getDayOfWeekShortString(year: Int, month: Int, day: Int): String {
    return LocalDate.of(year, month, day).dayOfWeek.getDisplayName(
        TextStyle.SHORT,
        Locale("ko", "KR")
    )
}

fun getDayOfWeekShortString(weekDay: Int): String {
    val dayOfEnum = DayOfWeek.of(weekDay)
    return dayOfEnum.getDisplayName(TextStyle.SHORT, Locale("ko", "KR"))
}

fun requestKeyboardFocus(editText: EditText) {
    val imm =
        editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, 0)
}

fun clearKeyboardFocus(editText: EditText) {
    val imm: InputMethodManager =
        editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

@SuppressLint("ClickableViewAccessibility")
fun getTouchOutListener(editText: EditText, onTouchOut : () -> Unit): OnTouchListener {
    val onTouchOutListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            val editTextRect = Rect()
            editText.getGlobalVisibleRect(editTextRect)
            if (!editTextRect.contains(x, y)) {
                editText.clearFocus()
                onTouchOut()
                editText.text?.clear()
                clearKeyboardFocus(editText)
            }
        }
        false
    }

    return onTouchOutListener
}
