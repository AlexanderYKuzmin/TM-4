package com.kuzmin.tm_4.feature.measurements.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.alpha
import kotlin.math.roundToInt

open class RippleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs){

    private var rippleX: Float? = null
    private var rippleY: Float? = null
    private var rippleRadius: Float? = null
    var maxRippleRadius: Float = 100f // Retrieve from resources
    var rippleColor: Int = 0x88888888.toInt()

    private val ripplePaint = Paint().apply {
        color = rippleColor
    }

    private val animationExpand = object : Runnable {
        override fun run() {
            rippleRadius?.let { radius ->
                if (radius < maxRippleRadius) {
                    rippleRadius = radius + maxRippleRadius * 0.1f
                    invalidate()
                    postDelayed(this, 10L)
                }
            }
        }
    }

    private val animationFade = object : Runnable {
        override fun run() {
            ripplePaint.color.let { color ->
                if (color.alpha > 10) {
                    ripplePaint.color = color.adjustAlpha(0.9f)
                    invalidate()
                    postDelayed(this, 10L)
                } else {
                    rippleX = null
                    rippleY = null
                    rippleRadius = null
                    invalidate()
                }
            }

        }
    }

    fun startRipple(x: Float, y: Float) {
        rippleX = x
        rippleY = y
        rippleRadius = maxRippleRadius * 0.15f
        ripplePaint.color = rippleColor

        animationExpand.run()
    }

    fun stopRipple() {
        if (rippleRadius != null) {
            animationFade.run()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val x = rippleX ?: return
        val y = rippleY ?: return
        val r = rippleRadius ?: return

        canvas.drawCircle(x, y, r, ripplePaint)
    }
}

    fun Int.adjustAlpha(factor: Float): Int =
        (this.ushr(24) * factor).roundToInt() shl 24 or (0x00FFFFFF and this)

    inline val Int.alpha: Int
        get() = (this shr 24) and 0xFF

