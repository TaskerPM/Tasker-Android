package com.tasker.android.home.presentation.task_view.list

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.R

class HomeListViewItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != state.itemCount - 1) {
            outRect.bottom =
                context.resources.getDimension(R.dimen.home_list_view_item_margin).toInt()
        }
    }
}