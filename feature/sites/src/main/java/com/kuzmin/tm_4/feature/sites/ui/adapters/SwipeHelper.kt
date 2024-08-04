package com.kuzmin.tm_4.feature.sites.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.tm_4.feature.sites.R
import com.kuzmin.tm_4.feature.sites.ui.custom.ImageBgdClickListener
import java.util.LinkedList
import java.util.Queue

//@SuppressLint("ClickableViewAccessibility")
abstract class SwipeHelper(
    val context: Context,
    private val recyclerView: RecyclerView,
    ) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val buttonWidth: Int = 250

    private var swipePos: Int = -1

    private var _swipeThreshold: Float = 0.5f
    val swipeThreshold: Float get() = _swipeThreshold

    private val buttonBuffer: MutableMap<Int, MutableList<ImageBgdButton>> = mutableMapOf()
    private var buttonList = mutableListOf<ImageBgdButton>()

    private val removerQueue: Queue<Int> = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            return if (contains(element)) false else super.add(element)
        }
    }

    private val gestureDetector: GestureDetector by lazy { GestureDetector(context, gestureListener) }
    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            for (button in buttonList) {
                if (button.onClick(e.x, e.y)) {
                    break
                }
            }
            return true
        }
    }

    private val onTouchListener = View.OnTouchListener { v: View, event: MotionEvent ->
        v.performClick()
        if (swipePos < 0) return@OnTouchListener false
        val point = Point(event.rawX.toInt(), event.rawY.toInt())

        val swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePos)
        val swipedItem = swipeViewHolder!!.itemView

        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)

        with(event) {
            if (action == ACTION_DOWN || action == ACTION_UP || action == ACTION_MOVE) {
                if (rect.top < point.y && rect.bottom > point.y) {
                    gestureDetector.onTouchEvent(event)
                } else {
                    removerQueue.add(swipePos)
                    swipePos = -1
                    recoverSwipedItem()
                }
            }
        }
        return@OnTouchListener false
    }

    abstract fun instantiateImageSaveButton(
        viewHolder: RecyclerView.ViewHolder,
        buffer: MutableList<ImageBgdButton>
    )

    init {
        recyclerView.setOnTouchListener(onTouchListener)

        attachSwipe()
    }

    @Synchronized
    private fun recoverSwipedItem() {
        while (removerQueue.isNotEmpty()) {
            val pos = removerQueue.poll() ?: -1
            if (pos > - 1) {
                recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    private fun attachSwipe() {
        ItemTouchHelper(this).attachToRecyclerView(recyclerView)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        if (swipePos != pos) removerQueue.add(swipePos)
        swipePos = pos

        if (buttonBuffer.contains(swipePos)) buttonList = buttonBuffer[swipePos]!!
        else buttonList.clear()

        buttonBuffer.clear()
        _swipeThreshold = 0.5f * buttonList.size * buttonWidth
        recoverSwipedItem()
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
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

        var pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipePos = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer = mutableListOf<ImageBgdButton>()
                if (!buttonBuffer.containsKey(pos)) {
                    instantiateImageSaveButton(viewHolder, buffer)
                    buttonBuffer[pos] = buffer
                } else {
                    buffer = buttonBuffer[pos]!!
                }
                translationX = dX * buffer.size * buttonWidth / itemView.width
                drawButton(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, translationX, dY, actionState, isCurrentlyActive)
    }

    private fun drawButton(
        c: Canvas,
        itemView: View,
        buffer: MutableList<ImageBgdButton>,
        pos: Int,
        translationX: Float
    ) {
        var right = itemView.right.toFloat()
        val dButtonWidth = -1 * translationX / buffer.size
        for (button in buffer) {
            val left = right - dButtonWidth
            button.onDraw(
                c,
                RectF(left, itemView.top.toFloat(), right, itemView.bottom.toFloat()),
                pos
            )
            right = left
        }
    }

    inner class ImageBgdButton(
        private val text: String,
        private val imageResId: Int,
        private val textSize: Float,
        val color: Int,
        private val listener: ImageBgdClickListener,
        val context: Context
    ) {

        private var clickRegion: RectF? = null
        private var pos: Int = -1

        private val paint = Paint().apply {
            color = this@ImageBgdButton.color
        }

        private val txtPaint = Paint().apply {
            color = Color.WHITE
            textSize = this@ImageBgdButton.textSize
            textAlign = Paint.Align.LEFT
        }

        private val txtRect = Rect()

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x,y)) {
                listener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
            c.drawRect(rectF, paint)

            val cHeight = rectF.height()
            val cWidth = rectF.width()

            txtPaint.getTextBounds(text, 0, text.length, txtRect)
            var x = 0f
            var y = 0f

            if (imageResId == 0) {
                x = cWidth / 2 - txtRect.width() / 2f - txtRect.left
                y = cHeight / 2 - txtRect.height() / 2f - txtRect.bottom
                c.drawText(text, rectF.left + x, rectF.top + y, txtPaint)
            } else {
                val drawable = getDrawable(context, imageResId) ?: throw RuntimeException("No drawable in resources.")
                val bitmap = drawableToBitmap(drawable)
                c.drawBitmap(bitmap, (rectF.left + rectF.right) / 2 - bitmap.width / 2, (rectF.top + rectF.bottom) / 2 - bitmap.height / 2, txtPaint)
            }
            clickRegion = rectF
            this.pos = pos
        }

        private fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) return drawable.bitmap

            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
}
