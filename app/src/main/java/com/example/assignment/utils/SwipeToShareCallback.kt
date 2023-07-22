package com.example.assignment.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToShareCallback(var context: Context, dragDir: Int, swipeDir: Int) :
    ItemTouchHelper.SimpleCallback(dragDir, swipeDir) {

    //configure right swipe params
    var rightBG: Int = Color.LTGRAY;
    var rightLabel: String = ""
    var rightIcon: Drawable? = null

    private lateinit var background: Drawable

    var initiated: Boolean = false

    //Setting Swipe Text
    private val paint = Paint()

    private fun initSwipeView(): Unit {
        paint.color = Color.WHITE
        paint.textSize = 48f
        paint.textAlign = Paint.Align.CENTER
        background = ColorDrawable();
        initiated = true;
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        if (!initiated) {
            initSwipeView()
        }
        if (dX > 0) {
            //right swipe
            val intrinsicHeight = (rightIcon?.intrinsicWidth ?: 0)
            val xMarkTop = itemView.top + ((itemView.bottom - itemView.top) - intrinsicHeight) / 2
            val xMarkBottom = xMarkTop + intrinsicHeight

            colorCanvas(
                c,
                rightBG,
                itemView.left + dX.toInt(),
                itemView.top,
                itemView.left,
                itemView.bottom
            )
            drawTextOnCanvas(
                c,
                rightLabel,
                (itemView.left + 200).toFloat(),
                (xMarkTop + 10).toFloat()
            )
            drawIconOnCanVas(
                c, rightIcon, itemView.left + (rightIcon?.intrinsicWidth ?: 0) + 50,
                xMarkTop + 20,
                itemView.left + 2 * (rightIcon?.intrinsicWidth ?: 0) + 50,
                xMarkBottom + 20
            )

        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    private fun colorCanvas(
        canvas: Canvas,
        canvasColor: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): Unit {
        (background as ColorDrawable).color = canvasColor
        background.setBounds(left, top, right, bottom)
        background.draw(canvas)
    }

    private fun drawTextOnCanvas(canvas: Canvas, label: String, x: Float, y: Float) {
        canvas.drawText(label, x, y, paint)
    }

    private fun drawIconOnCanVas(
        canvas: Canvas, icon: Drawable?, left: Int, top: Int, right: Int, bottom: Int
    ): Unit {
        icon?.setBounds(left, top, right, bottom)
        icon?.draw(canvas)

    }
}