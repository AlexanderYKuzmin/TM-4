package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.SiteTinyData

interface SitePrefManager {
    suspend fun readSiteData(): SiteTinyData

    suspend fun writeSiteData(siteTinyData: SiteTinyData)

    suspend fun writeMcUuid(mcUuid: String)
}