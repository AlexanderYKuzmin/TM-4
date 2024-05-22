package com.kuzmin.tm_4.feature.report.domain.model

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.kuzmin.tm_4.feature.report.domain.valueformatter.ProfileChartValueFormatter


abstract class ChartDataBuilder {
    var limitByHeight = 0
    var xValuesMax: Float = 0.0f
    var yValuesMax: Float = 0.0f

    lateinit var xyValues: List<Pair<Float, Float>>
    lateinit var xyValuesBorder: List<Pair<Float, Float>>

    val drawOrder = arrayOf(
        CombinedChart.DrawOrder.LINE,
        CombinedChart.DrawOrder.SCATTER
    )

    var descriptionText: String? = null

    val scatterDataSetList = mutableListOf<IScatterDataSet>()

    val lineDataSetList = mutableListOf<ILineDataSet>()

    abstract val chartXAxisLimitLines: List<LimitLine>
    abstract val chartYAxisLimitLines: List<LimitLine>

    lateinit var xAxis: XAxis
    lateinit var leftAxis: YAxis
    var rightAxis: YAxis? = null

    fun init() {
        xAxis = createAxisXData()
        leftAxis = createAxisLeftData()
        rightAxis = createAxisRightData()

        createLineDataSetList()
        createScatterDataSetList()
    }

    private fun createScatterDataSetList() {
        for (pair in xyValues) {
            scatterDataSetList.add(
                createScatterDataSet(pair)
            )
        }
    }

    private fun createScatterDataSet(xyData: Pair<Float, Float>): ScatterDataSet {
        Log.d("chart", "Create Scatter Data Set")
        return ScatterDataSet(
            listOf(Entry(xyData.first, xyData.second)), null
        ).apply {
            setScatterShape(ScatterChart.ScatterShape.CIRCLE)
            setDrawValues(true)
            form = Legend.LegendForm.NONE
            scatterShapeSize = 15f
            scatterShapeHoleColor = Color.BLUE
            color = Color.BLUE
            valueTextSize = 10f
            valueTypeface = Typeface.DEFAULT_BOLD
            valueTextColor = Color.BLACK
            valueFormatter = ProfileChartValueFormatter()
        }
    }

    private fun createLineDataSetList() {
        Log.d("chart", "Create LineDataSetList. xyValues = $xyValues")
        for (i in xyValues.indices) {
            if (i == xyValues.lastIndex) break
            lineDataSetList.add(
                createLineDataSet(listOf(xyValues[i], xyValues[i + 1]))
            )
        }
    }

    private fun createLineDataSet(xyData: List<Pair<Float, Float>>): LineDataSet {
        Log.d("chart", "Create Line DataSet")
        if (xyData.size < 2) throw RuntimeException("Wrong XY data.")

        val startEntry: Entry
        val endEntry: Entry

        when {
            xyData[0].first < xyData[1].first -> {
                startEntry = Entry(xyData[0].first, xyData[0].second)
                endEntry = Entry(xyData[1].first, xyData[1].second)
            }

            xyData[0].first > xyData[1].first -> {
                endEntry = Entry(xyData[0].first, xyData[0].second)
                startEntry = Entry(xyData[1].first, xyData[1].second)
            }

            else -> {
                startEntry = Entry(xyData[0].first, xyData[0].second)
                endEntry = Entry(xyData[1].first + 0.1f, xyData[1].second)
            }
        }
        Log.d("chart", "Create Line data set entries: ${startEntry}, ${endEntry}")
        return LineDataSet(listOf(startEntry, endEntry), null).apply {
            setDrawValues(false)
            setDrawCircles(false)
            form =
                if (lineDataSetList.size > 0) Legend.LegendForm.NONE else Legend.LegendForm.DEFAULT
            label = if (lineDataSetList.size < 1) AXIS_IN_FACT else null
            color = Color.BLUE
            fillColor = Color.BLUE
            lineWidth = 1f
        }
    }

    abstract fun createAxisXData(): XAxis

    abstract fun createAxisLeftData(): YAxis

    abstract fun createAxisRightData(): YAxis?

    fun createLimitLine(start: Float, color: Int): LimitLine {
        return LimitLine(start, null).apply {
            lineWidth = 1f
            lineColor = color
            labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
            textSize = 10f
        }
    }

    companion object {
        const val Y_OFFSET = 4f

        const val FORM_SIZE = 10f

        const val AXIS_IN_FACT = "По факту"
        const val RED_LINE = "Допуск"
    }
}