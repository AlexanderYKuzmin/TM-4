package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.ErrorContainer
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import javax.inject.Inject
import javax.inject.Named

class SiteValidator @Inject constructor(
    @Named("Site") private val errorContainer: ErrorContainer
) : Validator {

    override fun checkNoFault(): Boolean {
        val hasFault = getErrorMap().values.any { !it.isCorrect }
        return !hasFault
    }

    override fun registerId(id: Int, parentId: Int, condition: Condition) {
        errorContainer.add(
            ErrorInfo(
                viewId = id,
                condition = condition,
                parentId = parentId
            )
        )
    }

    override fun getErrorMap(): Map<Int, ErrorInfo> {
        return errorContainer.errorMap
    }

    override fun validate(id: Int, content: String?): ErrorInfo? {
        val errorInfo = errorContainer.errorMap[id] ?: return null

        val checkedErrInfo =  when(errorInfo.condition) {
            Condition.FOR_EMPTY -> {
                validateForEmpty(errorInfo, content)
            }
            Condition.GEO_VALUE -> {
                validateGeo(errorInfo, content)
            }
            else -> { null }
        }
            ?.let { errorContainer.update(it) }

        return checkedErrInfo
    }

    private fun validateForEmpty(errorInfo: ErrorInfo, text: String?): ErrorInfo {
         return if (text.isNullOrEmpty() || text.isBlank()) {
             errorInfo.copy(
                 isCorrect = false,
                 errorHelpTextId = R.string.must_be_filled,
                 errorHelpTextShortId = R.string.must_be_filled_short,
             )
         } else {
             errorInfo.copy(
                 isCorrect = true,
                 errorHelpTextId = null,
                 errorHelpTextShortId = null
             )
         }
    }

    private fun validateGeo(errorInfo: ErrorInfo, text: String?): ErrorInfo {
        val errorInfoTemp = validateForEmpty(errorInfo, text)
        if (!errorInfoTemp.isCorrect) return errorInfoTemp

        return if (text!!.matches(GEO_POS_PATTERN.toRegex())) {
            if (checkValue(errorInfo.viewId, text)) {
                errorInfo.copy(
                    isCorrect = true,
                    errorHelpTextId = null,
                    errorHelpTextShortId = null
                )
            } else {
                errorInfo.copy(
                    isCorrect = false,
                    errorHelpTextId = getWrongValueHelpTextStringIdByViewId(errorInfo.viewId),
                    errorHelpTextShortId = getWrongValueHelpTextStringIdByViewId(errorInfo.viewId)
                )
            }
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = getWrongFormatHelpTextStringIdByViewId(errorInfo.viewId),
                errorHelpTextShortId = getWrongFormatHelpTextStringIdByViewId(errorInfo.viewId),
            )
        }
    }

    private fun getWrongFormatHelpTextStringIdByViewId(viewId: Int): Int {
        return when (viewId) {
            R.id.et_site_latitude_creation -> R.string.geo_format_lat
            R.id.et_site_longitude_creation -> R.string.geo_format_lon
            else -> { throw RuntimeException("Wrong view ID to find string ID in resources")}
        }
    }

    private fun getWrongValueHelpTextStringIdByViewId(viewId: Int): Int {
        return when (viewId) {
            R.id.et_site_latitude_creation -> R.string.geo_wrong_value_latitude
            R.id.et_site_longitude_creation -> R.string.geo_wrong_value_longitude
            else -> { throw RuntimeException("Wrong view ID to find string ID in resources")}
        }
    }

    private fun checkValue(id: Int, text: String?): Boolean {
        val geo = text.toString().toDouble()

        return when (id) {
            R.id.et_site_latitude_creation -> {
                geo > 0 && geo <= 90.0
            }

            R.id.et_site_longitude_creation -> {
                geo > 0 && geo <= 180.0
            }

            else -> false
        }
    }

    companion object {
        private const val GEO_POS_PATTERN = "^\\d{1,3}((\\.\\d+)?)"
    }
}