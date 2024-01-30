package com.kuzmin.tm_4.common.extension

import android.annotation.SuppressLint
import android.util.Log
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

fun String.isNameConsistent(): Boolean {
    return trim().length > 2
}

fun String.isPasswordConsistent(): Boolean {
    return trim().length > 7
}

fun String.toSqlDate(): Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)?.let {
        Date(
            it.time
        )
    }
}

fun String.toDate(): java.util.Date? {
    try {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        Log.d("Global", "Wrong date format to parse Date.")
        return null
    }
}

fun String.getX(): Int {
    val xyArray = split("x")
    return xyArray[0].toInt()
}

fun String.getY(): Int {
    val xyArray = split("x")
    return xyArray[1].toInt()
}
