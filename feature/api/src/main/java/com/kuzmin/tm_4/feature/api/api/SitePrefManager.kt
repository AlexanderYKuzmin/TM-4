package com.kuzmin.tm_4.feature.api.api

interface SitePrefManager {
    suspend fun readSiteData(): Map<String, String>

    suspend fun writeSiteData(sUuid: String, cUuid: String ="", mcUuid: String = "")
}