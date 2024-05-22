package com.kuzmin.tm_4.feature.report.domain.model

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import com.kuzmin.tm_4.common.extension.roundToCeil
import com.kuzmin.tm_4.feature.report.domain.valueformatter.XOYValueFormatter
import javax.inject.Inject

class ChartDataBuilderXOY @Inject constructor(

) : ChartDataBuilder() {
    override val chartXAxisLimitLines: List<LimitLine>
        get() = listOf(
            createLimitLine(0f, Color.BLACK),
            createLimitLine(xValuesMax, Color.LTGRAY)
        )

    override val chartYAxisLimitLines: List<LimitLine>
        get() = listOf(
            createLimitLine(0f, Color.BLACK),
            createLimitLine(yValuesMax, Color.LTGRAY)
        )


    fun createScatterDataSetBorder(xyData: List<Pair<Float, Float>>) {
        val entriesBorder = mutableListOf<Entry>()
        for (i in xyData.indices) {
            with(xyData[i]) {
                entriesBorder.add(
                    Entry(first, second)
                )
            }
        }
        scatterDataSetList.add(
            ScatterDataSet(entriesBorder, RED_LINE).apply {
                color = Color.RED
                this.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
                setDrawValues(false)
            }
        )
    }

    override fun createAxisXData(): XAxis {
        return XAxis().apply {
            axisMaximum = (xValuesMax / 10).roundToCeil() * 10
            axisMinimum = -axisMaximum
            axisLineWidth = 2f
            labelCount = (xValuesMax / 10).roundToCeil().toInt() * 10 / xyValues.size * 2
            position = XAxis.XAxisPosition.BOTTOM
            removeAllLimitLines()
        }
    }

    override fun createAxisLeftData(): YAxis {
        return YAxis().apply {
            axisMaximum = (yValuesMax / 10).roundToCeil() * 10
            axisMinimum = -axisMaximum
            axisLineWidth = 2f
            labelCount = (yValuesMax / 10).roundToCeil().toInt() * 10 / xyValues.size * 2
            removeAllLimitLines()
        }
    }

    override fun createAxisRightData(): YAxis? {
        return null
    }
}