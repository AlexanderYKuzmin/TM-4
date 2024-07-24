package com.kuzmin.tm_4.feature.site_creation.domain.model

import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition

data class ErrorInfo(
    val viewId: Int = -1,

    val isCorrect: Boolean = false,

    val condition: Condition? = null,

    val errorHelpTextId: Int? = null,

    val errorHelpTextShortId: Int? = null,

    val errorToastId: Int? = null,

    val parentId: Int = -1
) {

}
