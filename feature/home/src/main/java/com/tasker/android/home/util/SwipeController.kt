package com.tasker.android.home.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.util.getColorFrom
import com.tasker.android.home.presentation.category.HomeCategoryViewAdapter

class SwipeController(
    private val context: Context,
    private val onSwiped: (Int) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val textPaint: Paint = Paint()
    private val backgroundPaint: Paint = Paint()

    init {
        textPaint.color = getColorFrom(context, com.tasker.android.common.R.color.white)
        textPaint.textSize = 30f
        backgroundPaint.color = getColorFrom(context, com.tasker.android.common.R.color.red_basic)
    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
    ): Int {
        if (viewHolder is HomeCategoryViewAdapter.HomeCategoryHeaderHolder ||
            viewHolder is HomeCategoryViewAdapter.HomeCategoryAddViewHolder
        ) return 0
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition

        if (direction == ItemTouchHelper.LEFT) {
            onSwiped(pos)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive) {
            val child = viewHolder.itemView
            val text = context.getString(com.tasker.android.common.R.string.task_delete)
            val x = child.right - 150f
            val y = child.top + child.height / 2f + textPaint.textSize / 2f
            c.drawRect(
                child.left.toFloat(),
                child.top.toFloat(),
                child.right.toFloat(),
                child.bottom.toFloat(),
                backgroundPaint
            )
            c.drawText(text, x, y, textPaint)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}