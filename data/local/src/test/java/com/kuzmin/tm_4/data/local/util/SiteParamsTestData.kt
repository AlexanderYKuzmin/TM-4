package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.SiteParams

object SiteParamsTestData {

    fun createTestSiteParams(sIndex: Int): SiteParams {
        return SiteParams(
            siteUuid = "$SITE_PREFIX$sIndex",
            name = Constants.SITE_NAME,
            description = Constants.SITE_DESCRIPTION,
            latitude = Constants.LATITUDE,
            longitude = Constants.LONGITUDE,
            siteType = Constants.SITE_TYPE,
            siteTypeDescription = Constants.SITE_TYPE_DESC,
            durability = Constants.DURABILITY
        )
    }

    fun createTestSiteParamsDb(sIndex: Int): SiteParamsDb {
        return SiteParamsDb(
            uuid = "$SITE_PREFIX$sIndex",
            siteUuid = "$SITE_PREFIX$sIndex",
            name = Constants.SITE_NAME,
            description = Constants.SITE_DESCRIPTION,
            latitude = Constants.LATITUDE,
            longitude = Constants.LONGITUDE,
            siteType = Constants.SITE_TYPE,
            siteTypeDescription = Constants.SITE_TYPE_DESC,
            durability = Constants.DURABILITY
        )
    }
}