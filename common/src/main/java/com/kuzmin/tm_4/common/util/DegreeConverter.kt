package com.kuzmin.tm_4.common.util

object DegreeConverter {

    const val RIGHT_SINGLE_QUOTE: Char = '\u2019'
    const val QUOTE: Char = '\u0022'
    const val DEGREE: Char = '\u00B0'
    const val LOW_SINGLE_QUOTE: Char = '\u201A'

    fun doubleToDegreeArray(coordinate: Double): Array<Int> {
        val degree = coordinate.toInt()
        val minutes = ((coordinate - degree) * 60).toInt()
        val seconds = ((((coordinate - degree) * 60) - minutes) * 60).toInt()
        return  arrayOf(
            degree,
            minutes,
            seconds
        )
    }
    fun degreeArrayToDouble(degMinSec: Array<Int>): Double {
        return degMinSec[0] + degMinSec[1].toDouble() / 60 + degMinSec[2].toDouble() / 3600
    }

    fun doubleToDegreeString(coordinate: Double): String {
        val elements = doubleToDegreeArray(coordinate)
        return String.format("%d%s%d%s%d%s", elements[0], DEGREE, elements[1], RIGHT_SINGLE_QUOTE, elements[2], QUOTE)
    }

    fun doublesPairToDegreeString(latitude: Double, longitude: Double): String {
        return String.format("%sN, %sE", doubleToDegreeString(latitude), doubleToDegreeString(longitude))
    }

    fun intToDegree(value: Int): String {
        return String.format("%d%s", value, DEGREE)
    }
}