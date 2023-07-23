package com.tasker.android.common.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tasker.android.common.R

class SwipeController(
    private val context: Context,
    private val onSwiped: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteBackgroundColor = ContextCompat.getColor(context, R.color.red_basic)
    private val deleteText = "삭제"
    private val deleteTextPaint = Paint().apply {
        color = Color.WHITE
        textSize = 36f
        textAlign = Paint.Align.LEFT
    }
    private val background = ColorDrawable()

    private val backgroundPaint = Paint().apply {
        color = deleteBackgroundColor
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

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
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        if (dX < 0) {
            // 배경 그리기
            val left = itemView.right + dX
            val top = itemView.top.toFloat()
            val right = itemView.right.toFloat()
            val bottom = itemView.bottom.toFloat()
            val radii = floatArrayOf(0f, 0f, 16f, 16f, 16f, 16f, 0f, 0f) // 오른쪽 모서리만 radius 적용
            val path = Path()
            path.addRoundRect(RectF(left, top, right, bottom), radii, Path.Direction.CW)
            c.drawPath(path, backgroundPaint)

            // 삭제 글자 그리기
            val deleteTextRect = Rect()
            deleteTextPaint.getTextBounds(deleteText, 0, deleteText.length, deleteTextRect)
            val textX = itemView.right.toFloat() - deleteTextRect.width() - 60 // 수정된 부분
            val textY = (itemView.top + (itemHeight / 2) + (deleteTextRect.height() / 2)).toFloat()
            c.drawText(deleteText, textX, textY, deleteTextPaint)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, Paint())
    }
}