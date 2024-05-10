package com.kuzmin.tm_4.common.util

object DistanceConverter {
    fun mmToMetersString(value: Int): String {
        val meters = value.toFloat() / 1000
        return "${meters}м"
    }
}