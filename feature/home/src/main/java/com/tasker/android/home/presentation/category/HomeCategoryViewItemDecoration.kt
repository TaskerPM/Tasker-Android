package com.tasker.android.home.presentation.category

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.home.R

class HomeCategoryViewItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        when (parent.getChildViewHolder(view)) {
            is HomeCategoryViewAdapter.HomeCategoryHeaderHolder -> {
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top =
                        context.resources.getDimensionPixelSize(R.dimen.home_category_view_item_header_top_margin)
                }
            }
        }
    }
}