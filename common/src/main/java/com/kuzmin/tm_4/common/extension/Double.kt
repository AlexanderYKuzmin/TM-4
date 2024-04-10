package com.kuzmin.tm_4.common.extension

fun Double.toMmInt(): Int {
    return (this * 1000).toInt()
}