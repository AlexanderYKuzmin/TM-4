package com.kuzmin.tm_4.common.extension

import kotlin.math.ceil

fun Float.roundToCeil(): Float {
    return ceil(this)
}