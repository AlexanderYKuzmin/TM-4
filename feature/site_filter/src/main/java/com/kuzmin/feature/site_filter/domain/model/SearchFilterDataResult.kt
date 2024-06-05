package com.kuzmin.feature.site_filter.domain.model

import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData

sealed class SearchFilterDataResult {
    class Succes(val searchFilterData: SearchFilterData): SearchFilterDataResult()

    class Error(val throwable: Throwable): SearchFilterDataResult()
}