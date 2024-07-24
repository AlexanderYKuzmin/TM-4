package com.kuzmin.tm_4.feature.api.extension

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children

fun View.clearEditTextFields() {
    if (this is ViewGroup && childCount > 0) {
        children.forEach { it.clearEditTextFields() }
    } else {
        if (this is EditText) setText("")
    }
}