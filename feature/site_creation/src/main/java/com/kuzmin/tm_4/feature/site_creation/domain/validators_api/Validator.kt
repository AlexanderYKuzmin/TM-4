package com.kuzmin.tm_4.feature.site_creation.domain.validators_api

import com.kuzmin.tm_4.feature.site_creation.domain.model.Condition
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

interface Validator {
    fun registerId(id: Int, parentId: Int, condition: Condition)

    fun validate(id: Int, content: String?): ErrorInfo?

    fun getErrorMap(): Map<Int, ErrorInfo>

    fun checkNoFault(): Boolean
}