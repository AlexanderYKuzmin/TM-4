package com.kuzmin.tm_4.feature.report.domain.valueformatter

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.pow
import kotlin.math.sqrt

class XOYValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry): String {
        return sqrt(
            with(entry) {
                (x.pow(2) + y.pow(2)).toDouble()
            }
        ).toInt().toString()
    }
}