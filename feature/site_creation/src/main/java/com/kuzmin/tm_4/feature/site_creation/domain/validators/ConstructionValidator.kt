package com.kuzmin.tm_4.feature.site_creation.domain.validators

import android.util.Log
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.DATE
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.HEIGHT
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.SECTIONS_Q
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.SECTION_HEIGHT
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition.SECTION_WIDTH_OR_TOP
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.ErrorContainer
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.StructureValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import javax.inject.Inject
import javax.inject.Named

class ConstructionValidator @Inject constructor(
    @Named("Construction") private val errorContainer: ErrorContainer
) : Validator {
    override fun registerId(id: Int, parentId: Int, condition: Condition) {
        errorContainer.add(
            ErrorInfo(
                viewId = id,
                condition = condition,
                parentId = parentId
            )
        )
    }

    override fun validate(id: Int, content: String?): ErrorInfo? {
        Log.d("Creation", "Validator validate. Content: $content")
        val errorInfo = errorContainer.errorMap[id] ?: return null

        validateForEmpty(errorInfo, content).also { if (!it.isCorrect) {
            Log.d("Creation", "Field Is Empty $content return it")
            return it
        } }

        val checkedErrInfo =  when(errorInfo.condition) {

            DATE -> validateDate(errorInfo, content)

            HEIGHT -> validateHeight(errorInfo, content)

            SECTIONS_Q -> validateQuantity(errorInfo, content)

            SECTION_HEIGHT -> validateSectionHeight(errorInfo, content)

            SECTION_WIDTH_OR_TOP -> validateSectionWidthTop(errorInfo, content)

            else ->  null
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

    private fun validateDate(errorInfo: ErrorInfo, content: String?): ErrorInfo {
        return if (content!!.matches(DATE_PATTERN.toRegex())) {
            getTrueErrorInfo(errorInfo)
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = R.string.date_field_error,
                errorHelpTextShortId = R.string.date_field_error,
            )
        }
    }

    private fun validateHeight(errorInfo: ErrorInfo, content: String?): ErrorInfo {
        val height = content!!.toInt()
        return if (height in 10000..120000) {
            getTrueErrorInfo(errorInfo)
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = R.string.height_field_error,
                errorHelpTextShortId = R.string.height_field_error,
            )
        }
    }

    private fun validateQuantity(errorInfo: ErrorInfo, content: String?): ErrorInfo {
        val quantity = content!!.toInt()
        return if (quantity in 1..30) {
            getTrueErrorInfo(errorInfo)
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = R.string.q_sections_field_error,
                errorHelpTextShortId = R.string.q_sections_field_error,
            )
        }
    }

    private fun validateSectionHeight(errorInfo: ErrorInfo, content: String?): ErrorInfo {
        val sectionHeight = content!!.toInt()
        return if (sectionHeight in 1000..30000) {
            getTrueErrorInfo(errorInfo)
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = R.string.section_height_field_error,
                errorHelpTextShortId = R.string.section_height_field_error,
            )
        }
    }

    private fun validateSectionWidthTop(errorInfo: ErrorInfo, content: String?): ErrorInfo {
        val sectionWidth = content!!.toInt()
        return if (sectionWidth in 100..20000) {
            getTrueErrorInfo(errorInfo)
        } else {
            errorInfo.copy(
                isCorrect = false,
                errorHelpTextId = R.string.section_width_top_error,
                errorHelpTextShortId = R.string.section_width_top_error,
            )
        }
    }

    private fun getTrueErrorInfo(errorInfo: ErrorInfo): ErrorInfo {
        return errorInfo.copy(
            isCorrect = true,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
        )
    }

    override fun getErrorMap(): Map<Int, ErrorInfo> {
        TODO("Not yet implemented")
    }

    override fun checkNoFault(): Boolean {
        Log.d("Creation", "size: ${errorContainer.errorMap.size},  error map: ${errorContainer.errorMap}")
        return true
    }

    companion object {
        const val DATE_PATTERN = "^[0-3][0-9]\\.[01][012]\\.[12][0-9]{3}"
    }
}