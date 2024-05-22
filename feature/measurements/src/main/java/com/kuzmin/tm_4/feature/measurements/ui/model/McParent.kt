package com.kuzmin.tm_4.feature.measurements.ui.model

import android.content.Context
import com.kuzmin.tm_4.feature.api.domain.model.site.McLevelInfo
import com.kuzmin.tm_4.feature.measurements.R
import java.util.Date

data class McParent(
    val uuid: String,

    val date: Date?,

    val employee: String,

    val isServiceable: Boolean,

    val levelsInfo: List<McLevelInfo>? = null
) {
    var isOpen = false

    fun getConclusion(context: Context): String {
        return if (isServiceable) {
            context.getString(R.string.serviceable)
        } else context.getString(R.string.not_serviceable)
    }
}
