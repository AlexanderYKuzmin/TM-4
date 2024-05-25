package com.kuzmin.tm_4.feature.report.util

import com.kuzmin.tm_4.common.extension.round
import com.kuzmin.tm_4.common.extension.roundToCeil
import com.kuzmin.tm_4.common.util.CommonConstants
import kotlin.math.hypot
import kotlin.math.sqrt

object ConstructionValues {
    fun isFinalExceeded(finalShift: Int, shiftLimit: Int): Boolean {
        return finalShift > shiftLimit
    }

    fun isProfileExceeded(shift: Int, shiftLimit: Int): Boolean {
        return shift > shiftLimit
    }

    fun defineShiftLimits(constructionType: String, height: Int): Int {
        return when (constructionType) {
            CommonConstants.TOWER -> height / 1000
            CommonConstants.MAST -> height / 1500
            CommonConstants.POLE -> height / 1000
            else -> throw RuntimeException("Wrong construction type.")
        }
    }

    fun defineFinalShift(config: String, shiftX: Int, shiftY: Int): Int {
        return when(config) {
            "4" -> hypot(shiftX.toDouble(), shiftY.toDouble()).round()
            "3" -> TODO()
            "0" -> TODO()
            else -> throw RuntimeException("Wrong construction configuration")
        }
    }
}