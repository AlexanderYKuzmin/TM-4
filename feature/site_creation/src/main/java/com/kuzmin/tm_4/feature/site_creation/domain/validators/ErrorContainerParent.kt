package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.ErrorContainer

abstract class ErrorContainerParent : ErrorContainer {

    private val _errorMap = mutableMapOf<Int, ErrorInfo>()

    override val errorMap: Map<Int, ErrorInfo>
        get() = _errorMap

    override fun update(item: ErrorInfo): ErrorInfo {
        _errorMap[item.viewId] = item
        return item
    }

    override fun add(item: ErrorInfo) {
        _errorMap[item.viewId] = item
    }
}