package com.kuzmin.tm_4.common.extension

import kotlin.math.ceil
import kotlin.math.round

fun Double.toMmInt(): Int {
    return (this * 1000).toInt()
}

fun Double.roundToCeil(): Int {
    return ceil(this).toInt()
}

fun Double.round(): Int {
    return round(this).toInt()
}