package com.kuzmin.tm_4.common.util

object DegreeConverter {
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
        return String.format("%d\u0020%d\'%d\"", elements[0], elements[1], elements[2])
    }

    fun doublesPairToDegreeString(latitude: Double, longitude: Double): String {
        return String.format("%sN, %sE", doubleToDegreeString(latitude), doubleToDegreeString(longitude))
    }
}