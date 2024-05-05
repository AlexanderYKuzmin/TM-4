package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.model.model_complex_obj.ConstructionFull
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.GroupFull
import com.kuzmin.tm_4.feature.api.model.model_complex_obj.McFull
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.model.site.Site

interface LocalRepository {
    suspend fun addSiteToDb(site: Site, durability: String)

    suspend fun getSite(siteUuid: String): Site?

    suspend fun getConstructionFull(cUuid: String): ConstructionFull?

    suspend fun getMc(mcUuid: String): MeasurementConstruction?

    suspend fun getMcFull(mcUuid: String): McFull?

    suspend fun getGroupFull(groupMum: Int, mcUuid: String): GroupFull?
}