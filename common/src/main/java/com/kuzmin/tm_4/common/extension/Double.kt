package com.kuzmin.tm_4.common.extension

import kotlin.math.ceil

fun Double.toMmInt(): Int {
    return (this * 1000).toInt()
}

fun Double.roundToCeil(): Int {
    return ceil(this).toInt()
}