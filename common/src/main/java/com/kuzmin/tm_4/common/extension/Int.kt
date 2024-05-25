package com.kuzmin.tm_4.common.extension

fun Int.toAltitudeString(): String {
    val str = this.toString()
    val char = ','
    if (str.isBlank()) return "failed"
    return when (str.length) {
        3 -> StringBuilder(str).apply {
            insert(
                0,
                "0$char"
            )
        }.toString()
        2 -> StringBuilder(str).apply {
            insert(
                0,
                "0${char}0"
            )
        }.toString()
        1 -> {
            StringBuilder(str).apply {
                insert(
                    0,
                    "0${char}00"
                )
            }.toString()
        }
        else -> {
            StringBuilder(str).apply {
                insert(
                    str.length - 3,
                    char
                )
            }.toString()
        }
    }
}