package com.kuzmin.tm_4.feature.report.domain.model

import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.kuzmin.tm_4.common.extension.roundToCeil
import com.kuzmin.tm_4.feature.report.domain.valueformatter.ProfileChartValueFormatter
import javax.inject.Inject

class ChartDataBuilderXYProfile @Inject constructor(

) : ChartDataBuilder(){

    override val chartXAxisLimitLines = listOf(LimitLine(0f, null).apply {
        lineWidth = 2f
        lineColor = Color.BLACK
        labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
        textSize = 10f
    })
    override val chartYAxisLimitLines: List<LimitLine>
        get() = emptyList()

    fun createLineDataSetBorder(xyData: List<Pair<Float, Float>>) {
        Log.d("chart", "Create Line data set border")
        val entriesBorder = mutableListOf<Entry>()
        for (i in xyData.indices) {
            with(xyData[i]) {
                entriesBorder.add(
                    Entry(first, second)
                )
            }
        }
        lineDataSetList.add(
            LineDataSet(entriesBorder, RED_LINE).apply {
                color = Color.RED
                lineWidth = 2f
                enableDashedLine(30f, 10f, 90f)
                setDrawValues(true)
                setDrawCircles(false)
                valueFormatter = ProfileChartValueFormatter()
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
        }
    }

    override fun createAxisLeftData(): YAxis {
        return YAxis().apply {
            axisMaximum = yValuesMax * 1.02f
            axisMinimum = 0f
            labelCount = xyValues.size - 1
            valueFormatter = ProfileChartValueFormatter()
        }
    }

    override fun createAxisRightData(): YAxis {
        return YAxis().apply {
            axisMaximum = yValuesMax * 1.02f
            axisMinimum = 0f
            labelCount = xyValues.size - 1
            valueFormatter = ProfileChartValueFormatter()
        }
    }
}