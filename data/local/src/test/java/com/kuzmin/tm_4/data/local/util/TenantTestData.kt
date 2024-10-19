package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.TenantDb
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.TENANT_NAME
import com.kuzmin.tm_4.data.local.util.Constants.TENANT_PREFIX
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Tenant

object TenantTestData {
    fun createTestTenant(sIndex: Int): Tenant {
        return Tenant(
            uuid = "$SITE_PREFIX$sIndex-$TENANT_PREFIX$sIndex",
            name = TENANT_NAME,
            logo = null
        )
    }

    fun createTestTenantDb(sIndex: Int): TenantDb {
        return TenantDb(
            uuid = "$SITE_PREFIX$sIndex-$TENANT_PREFIX$sIndex",
            name = TENANT_NAME,
            logo = null,
            siteUuid = "$SITE_PREFIX$sIndex"
        )
    }
}