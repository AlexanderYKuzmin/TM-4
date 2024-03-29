package com.kuzmin.tm_4.common.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import java.util.prefs.Preferences


fun Context.dpToPx(dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    )
}

fun Context.dpToIntPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.getDrawable(drawable: Int): Drawable {
    return ContextCompat.getDrawable(this, drawable)!!
}

fun Context.getString(string: Int): String {
    return ContextCompat.getString(this, string)
}