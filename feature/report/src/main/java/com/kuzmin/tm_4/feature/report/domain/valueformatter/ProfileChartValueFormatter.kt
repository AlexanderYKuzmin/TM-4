package com.kuzmin.tm_4.feature.report.domain.valueformatter

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter

class ProfileChartValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return (value / 1000).toInt().toString()
    }

    override fun getPointLabel(entry: Entry?): String {
        return "      " + entry!!.x.toInt().toString()
    }
}