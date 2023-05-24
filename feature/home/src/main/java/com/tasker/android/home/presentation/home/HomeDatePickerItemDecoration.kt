package com.tasker.android.home.presentation.home

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.R

class HomeDatePickerItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != parent.childCount) {
            outRect.right =
                context.resources.getDimension(R.dimen.home_date_picker_margin).toInt()
        }
    }
}