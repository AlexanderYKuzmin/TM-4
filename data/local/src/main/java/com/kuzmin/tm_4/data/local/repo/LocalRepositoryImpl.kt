package com.kuzmin.tm_4.data.local.repo

import android.util.Log
import com.kuzmin.tm_4.core.database.TmDao
import com.kuzmin.tm_4.data.local.mapper.ConstructionFullDbToConstructionFullMapper
import com.kuzmin.tm_4.data.local.mapper.MeasurementConstructionFullDbToMeasurementConstructionFullMapper
import com.kuzmin.tm_4.data.local.mapper.SiteModelToSiteDbMapper
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.ConstructionFull
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.GroupFull
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.McFull
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.model.site.Site
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val tmDao: TmDao,
    private val siteModelToDbMapper: SiteModelToSiteDbMapper,
    private val constructionDbToSiteModelMapper: ConstructionFullDbToConstructionFullMapper,
    private val mcDbToModelMapper: MeasurementConstructionFullDbToMeasurementConstructionFullMapper
) : LocalRepository {
    override suspend fun addSiteToDb(site: Site, durability: String) {
        Log.d("Db", "Add site to db. $site, mc: ${site.measurementsConstructions}")
        val siteDb = siteModelToDbMapper.mapSiteModelToSiteDb(site, durability)
        Log.d("Db", "Add site to db. Site DB: $siteDb, mc: ${siteDb.measurementsConstructions}")
        tmDao.addSite(siteDb)
    }

    override suspend fun getSite(siteUuid: String): Site? {
        val siteDb = tmDao.getSite()

        return null
    }

    override suspend fun getConstructionFull(cUuid: String): ConstructionFull {
        return constructionDbToSiteModelMapper.mapConstructionFullDbToConstructionFull(
            tmDao.getConstructionFull(cUuid)
        )
    }

    override suspend fun getMc(mcUuid: String): MeasurementConstruction {
        Log.d("MC", "Local repository GET MC")
        val mc = tmDao.getMc(mcUuid)
        return mcDbToModelMapper.mapMeasurementConstructionDbToMeasurementConstruction(mc)
    }

    override suspend fun getMcFull(mcUuid: String): McFull? {
        Log.d("db", "Measurement construction full: ${tmDao.getMcFull(mcUuid)}")
        return null
    }

    override suspend fun getGroupFull(groupMum: Int, mcUuid: String): GroupFull? {
        TODO("Not yet implemented")
    }
}