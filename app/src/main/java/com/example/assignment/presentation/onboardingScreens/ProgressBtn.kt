package com.example.assignment.presentation.onboardingScreens

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.assignment.R


class ProgressBtn(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var paint1: Paint = Paint()
    private var paint2: Paint = Paint()
    private var centerOfX = 0F
    private var centerOfY = 0F
    private var pageNo: Int

    init {
        var attributeArray: TypedArray? = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressBtn, 0, 0
        )
        pageNo = attributeArray?.getInteger(R.styleable.ProgressBtn_intro_page_no, 1)!!
        paint1.strokeWidth = 3F
        paint1.style = Paint.Style.STROKE
        paint1.strokeJoin = Paint.Join.ROUND
        paint1.strokeCap = Paint.Cap.ROUND
        paint1.color = Color.WHITE
        paint2.style = Paint.Style.FILL
        paint2.strokeJoin = Paint.Join.ROUND
        paint2.strokeCap = Paint.Cap.ROUND
        paint2.color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas?) {
        when (pageNo) {
            1 -> arc(120F, canvas, width.toFloat(), height.toFloat())
            2 -> arc(240F, canvas, width.toFloat(), height.toFloat())
            3 -> {
                centerOfX = (width / 2).toFloat()
                centerOfY = (height / 2).toFloat()
                canvas?.drawCircle(centerOfX, centerOfY, 100F, paint2)
            }
        }
        super.onDraw(canvas)
    }

    fun arc(sweep: Float, canvas: Canvas?, width: Float, height: Float) {
        centerOfX = (width / 2).toFloat()
        centerOfY = (height / 2).toFloat()
        canvas?.drawCircle(centerOfX, centerOfY, 70F, paint2)
        canvas?.drawArc(
            2F,
            2F,
            width - 2, height - 2, 270F, sweep, false, paint1
        )
        paint1.alpha = 80
        val sw = if (sweep == 120F) 30F else 150F
        canvas?.drawArc(
            2F,
            2F,
            width - 2, height - 2, sw, 360 - sweep, false, paint1
        )
        paint1.alpha = 255
        val icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_forward, null)
        icon?.setBounds(
            width.toInt() / 5,
            height.toInt() / 5,
            width.toInt() * 4 / 5, height.toInt() * 4 / 5
        )
        icon?.draw(canvas!!)
    }
}