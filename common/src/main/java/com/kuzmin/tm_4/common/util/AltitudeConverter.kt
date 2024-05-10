package com.kuzmin.tm_4.common.util

object AltitudeConverter {
    fun intToAltitudeString(value: Int): String {
        val ceil = value / 1000
        val tail = value - ceil * 1000

        var tailStr = when {
                tail == 0 -> "000"
                tail < 10 -> "00$tail"
                tail < 100 -> "0$tail"
                tail < 1000 -> tail.toString()
                else -> throw RuntimeException("Wrong level value.")
            }
        return String.format("+%d,%s", ceil, tailStr)
    }
}