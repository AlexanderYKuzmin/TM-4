package com.kuzmin.tm_4.feature.site_creation.domain.validators_api

import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

interface ErrorContainer {

    //fun get(): Map<Int, ErrorInfo>
    val errorMap: Map<Int, ErrorInfo>

    fun update(item: ErrorInfo): ErrorInfo

    fun add(item: ErrorInfo)
}