package com.kuzmin.tm_4.feature.measurements.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class TowerMeasureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}