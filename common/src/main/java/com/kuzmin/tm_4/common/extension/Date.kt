package com.kuzmin.tm_4.common.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToDateString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToDateSqlString(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToDateTimeString(): String {
    val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToTime(): String {
    val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToDateTimeUpToSeconds(): String {
    val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun Date.hours(): Int {
    return (this.time / (3600 * 1000)).toInt()
}