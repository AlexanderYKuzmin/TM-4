package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.SiteDataStore

interface SitePrefManager {
    suspend fun readSiteData(): SiteDataStore

    suspend fun writeSiteData(siteDataStore: SiteDataStore)
}