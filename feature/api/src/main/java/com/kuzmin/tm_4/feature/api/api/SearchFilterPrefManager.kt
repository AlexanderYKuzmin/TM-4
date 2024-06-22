package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData

interface SearchFilterPrefManager {
    suspend fun writeFilterData(data: SearchFilterData)

    suspend fun readFilterData(): SearchFilterData
}