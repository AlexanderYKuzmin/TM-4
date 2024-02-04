package com.kuzmin.tm_4.feature.sites.domain.model.samples

import com.kuzmin.tm_4.common.util.CommonConstants.MAST_RU
import com.kuzmin.tm_4.common.util.CommonConstants.POLE_RU
import com.kuzmin.tm_4.common.util.CommonConstants.TOWER_RU
import java.util.Date

data class ConstructionSample (
    val constructionType: String,

    val config: String,

    val heightMm: Int,

    val creationDate: Date? = null,        //yyyy-MM-dd

    val completedDate: Date? = null,       //yyyy-MM-dd

    val isCompleted: Boolean
) {
    fun getTypeRuValue(): String {
        return when(constructionType.lowercase()) {
            "tower" -> TOWER_RU
            "mast" -> MAST_RU
            "pole" -> POLE_RU
            else -> throw RuntimeException("Unknown construction type.")
        }
    }

    fun getHeight(): String {
        return String.format("%.1fм", heightMm.toFloat() / 1000)
    }
}