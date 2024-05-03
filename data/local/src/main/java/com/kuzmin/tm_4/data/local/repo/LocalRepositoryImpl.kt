package com.kuzmin.tm_4.data.local.repo

import android.util.Log
import com.kuzmin.tm_4.core.database.TmDao
import com.kuzmin.tm_4.data.local.mapper.SiteModelToSiteDbMapper
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.model.site.Site
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val tmDao: TmDao,
    private val mapper: SiteModelToSiteDbMapper
) : LocalRepository {
    override suspend fun addSiteToDb(site: Site, durability: String) {
        tmDao.addSite(
            mapper.mapSiteModelToSiteDb(site, durability)
        )
    }

    override suspend fun getSite(siteUuid: String): Site? {
        val siteDb = tmDao.getSite()

        Log.d("db", "Site from DB: $siteDb")
        return null
    }
}