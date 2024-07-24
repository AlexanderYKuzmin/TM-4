package com.kuzmin.tm_4.feature.site_creation.util

import com.kuzmin.tm_4.feature.site_creation.R
import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

object ConstructionValidatorData {
    fun getErrorInfoStaticItemsTest(): List<ErrorInfo> {
        return listOf(
            ErrorInfo(
                viewId = R.id.et_construction_date_creation,
                isCorrect = false,
                condition = Condition.DATE,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = R.id.til_construction_date_creation
            ),
            ErrorInfo(
                viewId = R.id.et_construction_height_creation,
                isCorrect = false,
                condition = Condition.HEIGHT,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = R.id.til_construction_height_creation
            ),
            ErrorInfo(
                viewId = R.id.et_construction_q_sections_creation,
                isCorrect = false,
                condition = Condition.SECTIONS_Q,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = R.id.til_construction_q_sections_creation
            ),
            ErrorInfo(
                viewId = R.id.et_section_height_creation + 100 * 3,
                isCorrect = false,
                condition = Condition.SECTION_HEIGHT,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 100
            ),
            ErrorInfo(
                viewId = R.id.et_section_bottom_creation + 100 * 3,
                isCorrect = false,
                condition = Condition.SECTION_WIDTH_OR_TOP,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 100
            ),
            ErrorInfo(
                viewId = R.id.et_section_top_creation + 100 * 3,
                isCorrect = false,
                condition = Condition.SECTION_WIDTH_OR_TOP,
                errorHelpTextId = null,
                errorHelpTextShortId = null,
                errorToastId = null,
                parentId = 100
            )
        )
    }

    fun getErrorInfoWhenDateWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_date_creation,
            isCorrect = false,
            condition = Condition.DATE,
            errorHelpTextId = R.string.date_field_error,
            errorHelpTextShortId = R.string.date_field_error,
            errorToastId = null,
            parentId = R.id.til_construction_date_creation
        )
    }

    fun getErrorInfoWhenDateOk(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_date_creation,
            isCorrect = true,
            condition = Condition.DATE,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = R.id.til_construction_date_creation
        )
    }

    fun getErrorInfoWhenHeightWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_height_creation,
            isCorrect = false,
            condition = Condition.HEIGHT,
            errorHelpTextId = R.string.height_field_error,
            errorHelpTextShortId = R.string.height_field_error,
            errorToastId = null,
            parentId = R.id.til_construction_height_creation
        )
    }

    fun getErrorInfoWhenHeightOk(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_height_creation,
            isCorrect = true,
            condition = Condition.HEIGHT,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = R.id.til_construction_height_creation
        )
    }

    fun getErrorInfoWhenSectionQWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_q_sections_creation,
            isCorrect = false,
            condition = Condition.SECTIONS_Q,
            errorHelpTextId = R.string.q_sections_field_error,
            errorHelpTextShortId = R.string.q_sections_field_error,
            errorToastId = null,
            parentId = R.id.til_construction_q_sections_creation
        )
    }

    fun getErrorInfoWhenSectionQOk(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_construction_q_sections_creation + 100 * 3,
            isCorrect = true,
            condition = Condition.SECTIONS_Q,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = R.id.til_construction_q_sections_creation
        )
    }

    fun getErrorInfoWhenSectionHeightWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_section_height_creation + 100 * 3,
            isCorrect = false,
            condition = Condition.SECTION_HEIGHT,
            errorHelpTextId = R.string.section_height_field_error,
            errorHelpTextShortId = R.string.section_height_field_error,
            errorToastId = null,
            parentId = 100
        )
    }

    fun getErrorInfoWhenSectionHeightOk(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_section_height_creation + 100 * 3,
            isCorrect = true,
            condition = Condition.SECTION_HEIGHT,
            errorHelpTextId = null,
            errorHelpTextShortId = null,
            errorToastId = null,
            parentId = 100
        )
    }

    fun getErrorInfoWhenSectionBottomWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_section_bottom_creation + 100 * 3,
            isCorrect = false,
            condition = Condition.SECTION_WIDTH_OR_TOP,
            errorHelpTextId = R.string.section_width_top_error,
            errorHelpTextShortId = R.string.section_width_top_error,
            errorToastId = null,
            parentId = 100
        )
    }

    fun getErrorInfoWhenSectionTopWrong(): ErrorInfo {
        return ErrorInfo(
            viewId = R.id.et_section_top_creation + 100 * 3,
            isCorrect = false,
            condition = Condition.SECTION_WIDTH_OR_TOP,
            errorHelpTextId = R.string.section_width_top_error,
            errorHelpTextShortId = R.string.section_width_top_error,
            errorToastId = null,
            parentId = 100
        )
    }
}