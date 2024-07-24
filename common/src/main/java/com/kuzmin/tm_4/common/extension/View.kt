package com.kuzmin.tm_4.common.extension

import android.widget.EditText

fun EditText.toIntOrZero(): Int {
    return this.text.toString().toIntOrNull() ?: 0
}