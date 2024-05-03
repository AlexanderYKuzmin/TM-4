package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.model.site.Site

interface LocalRepository {
    suspend fun addSiteToDb(site: Site, durability: String)

    suspend fun getSite(siteUuid: String): Site?
}