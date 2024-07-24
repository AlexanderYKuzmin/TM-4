package com.kuzmin.tm_4.feature.measurements.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.kuzmin.tm_4.common.util.AltitudeConverter
import com.kuzmin.tm_4.common.util.DegreeConverter
import com.kuzmin.tm_4.common.util.DistanceConverter
import com.kuzmin.tm_4.feature.api.domain.model.model_complex.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.site.Measurement
import com.kuzmin.tm_4.feature.measurements.R
import com.kuzmin.tm_4.feature.measurements.ui.customview.TowerMeasureView.NodeSide.*

class TowerMeasureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RippleView(context, attrs) {

    private var _constructionFull: ConstructionFull? = null

    private var _groupNum: Int = 0

    private var measurementsByAzimuth: List<Measurement>? = null

    private var levelValues = mutableListOf<String>()

    private var _mcUuid: String? = null

    private val startX = 100f
    private val startY = 100f

    private val towerNodes: MutableList<Pair<Node, Node>> = mutableListOf()

    private var textWidth = 0f
    private var rectBack = Rect()


    private val towerPaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val nodeFillPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private val textPaint = Paint().apply {
        strokeWidth = 10f
        color = Color.GREEN
        textSize = 50f
        setBackgroundColor(Color.BLACK)
    }.also {
        textWidth = it.measureText("000.0000")
    }

    private val bgdTextPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    fun populate(constructionFull: ConstructionFull, groupNum: Int, mcUuid: String?) {
        Log.d("TM", "Populate construction full: ${constructionFull}")
        _constructionFull = constructionFull
        _groupNum = groupNum
        _mcUuid = mcUuid
        measurementsByAzimuth = prepareMeasurementDataByGroup(groupNum)
        prepareLevelValues()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (_constructionFull == null) return
        background = AppCompatResources.getDrawable(context, com.kuzmin.tm_4.common.R.color.black)
        setNodes()

        drawFrameAndNodes(canvas)
        //Log.d("T", "tower nodes size = ${towerNodes.size}")

        if (measurementsByAzimuth == null) return

        drawTexts(canvas)

        drawGroupData(canvas)
    }

    private fun drawFrameAndNodes(canvas: Canvas) {
        // levels
        canvas.drawLines(towerNodes.flatMap {
            arrayOf(it.first.x, it.first.y, it.second.x, it.second.y).asIterable()
        }.toFloatArray(), towerPaint)

        val listOfLeftNodes = mutableListOf<Float>()
        val listOfRightNodes = mutableListOf<Float>()

        val listLeftDiagons = mutableListOf<Float>()
        val listRightDiagons = mutableListOf<Float>()

        for (i in towerNodes.indices) {
            if (i > 0) {
                listOfLeftNodes.add(towerNodes[i].first.x)
                listOfLeftNodes.add(towerNodes[i].first.y)

                listOfRightNodes.add(towerNodes[i].second.x)
                listOfRightNodes.add(towerNodes[i].second.y)

                listLeftDiagons.add(towerNodes[i].second.x)
                listLeftDiagons.add(towerNodes[i].second.y)

                listLeftDiagons.add(towerNodes[i - 1].first.x)
                listLeftDiagons.add(towerNodes[i - 1].first.y)

                listRightDiagons.add(towerNodes[i].first.x)
                listRightDiagons.add(towerNodes[i].first.y)

                listRightDiagons.add(towerNodes[i - 1].second.x)
                listRightDiagons.add(towerNodes[i - 1].second.y)
            }
            listOfLeftNodes.add(towerNodes[i].first.x)
            listOfLeftNodes.add(towerNodes[i].first.y)

            listOfRightNodes.add(towerNodes[i].second.x)
            listOfRightNodes.add(towerNodes[i].second.y)

        }
        canvas.drawLines(listOfLeftNodes.toFloatArray(), towerPaint)
        canvas.drawLines(listOfRightNodes.toFloatArray(), towerPaint)

        canvas.drawLines(listLeftDiagons.toFloatArray(), towerPaint)
        canvas.drawLines(listRightDiagons.toFloatArray(), towerPaint)

        towerNodes.forEach {
            canvas.drawCircle(it.first.x, it.first.y,  NODE_RADIUS, towerPaint)
            canvas.drawCircle(it.first.x, it.first.y,  NODE_RADIUS - 2f,
                setColorFillPaint(it.first.value)
            )

            canvas.drawCircle(it.second.x, it.second.y,  NODE_RADIUS, towerPaint)
            canvas.drawCircle(it.second.x, it.second.y,  NODE_RADIUS - 2f,
                setColorFillPaint(it.second.value)
            )
        }
    }

    private fun drawGroupData(canvas: Canvas) {
        //textPaint.apply { textSize = 30f }

        val groups = _constructionFull?.measurementConstructionFullList
            ?.first { it.mc.uuid == _mcUuid }
            ?.groups

        val azimuthName = context.getText(R.string.azimuth).toString()
        val theoDistanceName = context.getText(R.string.theo_distance).toString()
        val theoHeightName = context.getText(R.string.theo_height).toString()

        var azimuth = ""
        var theoDistance = ""
        var theoHeight = ""

        if (!groups.isNullOrEmpty()) {
            val group = groups.first { it.groupNum == _groupNum }

            azimuth = DegreeConverter.intToDegree(group.azimuth)

            theoDistance = DistanceConverter.mmToMetersString(group.theoDistance)

            theoHeight = DistanceConverter.mmToMetersString(group.theoHeight)
        }

        canvas.drawText(
            "$azimuthName $azimuth",
            TEXT_OFFSET_X,
            TEXT_OFFSET_Y,
            textPaint
        )
        canvas.drawText(
            "$theoDistanceName $theoDistance",
            TEXT_OFFSET_X,
            TEXT_OFFSET_Y + 50f,
            textPaint
        )
        canvas.drawText(
            "$theoHeightName $theoHeight",
            TEXT_OFFSET_X,
            TEXT_OFFSET_Y + 100f,
            textPaint
        )

    }

    private fun drawTexts(canvas: Canvas) {
        if (levelValues.isNullOrEmpty()) return
        for (i in towerNodes.indices) {

            val currentTextWidthL = textPaint.measureText(measurementsByAzimuth!![i].leftAngleCl.toString())
            val currentTextWidthR = textPaint.measureText(measurementsByAzimuth!![i].rightAngleCl.toString())
            val currentLevelTextWidth = textPaint.measureText(levelValues[i])

            canvas.drawRect(
                towerNodes[i].first.x - currentTextWidthL / 2 - 5f,
                towerNodes[i].first.y - TEXT_OFFSET_Y - textPaint.textSize,
                towerNodes[i].first.x + currentTextWidthL / 2 + 5f,
                towerNodes[i].first.y - TEXT_OFFSET_Y + 5f,
                bgdTextPaint
            )

            canvas.drawRect(
                towerNodes[i].second.x - currentTextWidthR / 2 - 5f,
                towerNodes[i].second.y - TEXT_OFFSET_Y - textPaint.textSize,
                towerNodes[i].second.x + currentTextWidthR / 2 + 5f,
                towerNodes[i].second.y - TEXT_OFFSET_Y + 5f,
                bgdTextPaint
            )

            canvas.drawRect(
                width / 2 - currentLevelTextWidth / 2 - 5f,
                towerNodes[i].first.y - TEXT_OFFSET_Y / 2 - textPaint.textSize,
                width / 2 + currentLevelTextWidth / 2 + 5f,
                towerNodes[i].first.y - TEXT_OFFSET_Y / 2 + 5f,
                bgdTextPaint
            )

            canvas.drawText(
                DegreeConverter.doubleToDegreeString(measurementsByAzimuth!![i].leftAngleCl),
                towerNodes[i].first.x - currentTextWidthL / 2,
                towerNodes[i].first.y - TEXT_OFFSET_Y,
                textPaint
            )
            canvas.drawText(
                DegreeConverter.doubleToDegreeString(measurementsByAzimuth!![i].rightAngleCl),
                towerNodes[i].second.x - currentTextWidthR / 2,
                towerNodes[i].second.y - TEXT_OFFSET_Y,
                textPaint
            )
            canvas.drawText(
                levelValues[i],
                width / 2 - currentTextWidthL / 2,
                towerNodes[i].first.y - TEXT_OFFSET_Y / 2,
                textPaint.apply { textSize = 30f }
            )

            textPaint.apply { textSize = 50f }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val node = getTapNode(x, y)
                if (node != null) { startRipple(node.x, node.y) }
            }
            MotionEvent.ACTION_UP -> {
                stopRipple()
            }
        }
        return true
    }

    private fun setColorFillPaint(value: Double?): Paint {
        return if (value == null) {
            nodeFillPaint.apply { color = Color.BLACK }
        } else {
            nodeFillPaint.apply { color = Color.GREEN }
        }
    }

    private fun setNodes() {
        //Log.d("T", "Set Nodes")
        towerNodes.clear()
        var widthKoef =
            (width - 2 * OFFSET_X ) / _constructionFull!!.constructionAndSections.sections.first { it.number == 1 }.wBottom
        var heightKoef = (height - 2 * OFFSET_Y) / _constructionFull!!.constructionAndSections.construction.height

        val sortedSections = _constructionFull!!.constructionAndSections.sections.sortedBy { it.number }

        if (sortedSections[0].wBottom == sortedSections[sortedSections.size - 1].wBottom) {
            widthKoef /= 4
        }

        var xShift = OFFSET_X
        var yShift = OFFSET_Y

        val xCenter = width / 2
        for (i in sortedSections.indices) {
            towerNodes.add(
                Pair(
                    Node(
                        xCenter.toFloat() - sortedSections[i].wBottom * widthKoef / 2,
                        height.toFloat() - yShift,
                        LEFT,
                        i,
                        setAngleValue(i, LEFT)
                    ),
                    Node(
                        xCenter.toFloat() + sortedSections[i].wBottom * widthKoef / 2,
                        height.toFloat() - yShift,
                        RIGHT,
                        i,
                        setAngleValue(i, RIGHT)
                    )
                )
            )

            //xShift += widthKoef * (sortedSections[i].wBottom - sortedSections[i].wTop)
            yShift += heightKoef * sortedSections[i].height
        }

        towerNodes.add(
            Pair(
                Node(
                    xCenter.toFloat() - sortedSections.last().wTop * widthKoef / 2,
                    height.toFloat() - yShift,
                    LEFT,
                    sortedSections.size,
                    setAngleValue(sortedSections.size, LEFT)
                ),
                Node(
                    xCenter.toFloat() + sortedSections.last().wTop * widthKoef / 2,
                    height.toFloat() - yShift,
                    RIGHT,
                    sortedSections.size,
                    setAngleValue(sortedSections.size, RIGHT)
                )
            )
        )
    }

    private fun setAngleValue(index: Int, side: NodeSide): Double? {
        return if (measurementsByAzimuth!!.size - 1 >= index) {
            when (side) {
                LEFT -> measurementsByAzimuth!![index].leftAngleCl
                RIGHT -> measurementsByAzimuth!![index].rightAngleCl
                else -> null
            }
        } else null
    }

    private fun prepareMeasurementDataByGroup(groupNum: Int): List<Measurement> {
        val mc = _constructionFull!!.measurementConstructionFullList
            .first { it.mc.uuid == _mcUuid }
        val groupUuidByNum = mc
            .groups?.first { it.groupNum == groupNum }?.uuid
        return mc.measurements
            ?.filter { it.measurementGroupUuid == groupUuidByNum }
            ?.sortedBy { it.level }
            ?: emptyList()
    }

    private fun prepareLevelValues() {
        if (_constructionFull == null) throw RuntimeException("No construction to draw.")
        val sections = _constructionFull!!.constructionAndSections.sections

        var altitude = 0
        levelValues.add(AltitudeConverter.intToAltitudeString(altitude))
        for (section in sections) {
            altitude += section.height
            levelValues.add(AltitudeConverter.intToAltitudeString(altitude))
        }
    }

    private fun getNodeSide(x: Float): NodeSide {
        return when {
            x < width / 2 -> LEFT
            x >= width / 2 -> RIGHT
            else -> NONE
        }
    }

    private fun getTapNode(x: Float, y: Float): Node? {
         return when (getNodeSide(x)) {
             LEFT ->
                 towerNodes.firstOrNull {
                     it.first.x - 100f <= x && x < it.first.x + 100f
                             && it.first.y + 100f >= y && y > it.first.y - 100f
                 }?.first
             RIGHT ->
                 towerNodes.firstOrNull {
                     it.second.x - 100f <= x && x < it.second.x + 100f
                             && it.second.y + 100f >= y && y > it.second.y - 100f
                 }?.second
             else -> null
         }
    }

    /*override fun onSaveInstanceState(): Parcelable {
        val savedState = Toolbar.SavedState(super.onSaveInstanceState())
        //savedState.ssIsOpened = isOpened
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        *//*if (state is SavedState) {
            _isOpened = state.ssIsOpened
            visibility = if (isOpened) VISIBLE else GONE*//*
        //}
    }*/



    private fun View.addRipple() = with(TypedValue()) {
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
        setBackgroundResource(resourceId)
    }

    private fun View.addCircleRipple() = with(TypedValue()) {
        context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
        setBackgroundResource(resourceId)
    }

    companion object {

        private const val OFFSET_X = 100f
        private const val OFFSET_Y = 130f

        private const val NODE_RADIUS = 30f

        private const val TEXT_OFFSET_X = 25f
        private const val TEXT_OFFSET_Y = 80f
    }

    data class Node(
        val x: Float,
        val y: Float,
        val side: NodeSide,
        val level: Int,
        val value: Double? = null
    )

    enum class NodeSide {
        LEFT,
        RIGHT,
        NONE
    }
}