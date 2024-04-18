package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.common.util.CommonConstants.C
import com.kuzmin.tm_4.common.util.CommonConstants.MC
import com.kuzmin.tm_4.common.util.CommonConstants.S
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.CONSTRUCTION_UUID
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.MEASUREMENT_CONSTRUCTION_UUID
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.SITE_UUID
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStoreSite: DataStore<Preferences> by preferencesDataStore(name = "site_data")

class SitePrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SitePrefManager{
    private val dataStore = appContext.dataStoreSite

    override suspend fun readSiteData(): Map<String, String> {
        return dataStore.data.map { prefs ->
            val sUuid = prefs[SITE_UUID] ?: ""
            val cUuid = prefs[CONSTRUCTION_UUID] ?: ""
            val mcUuid = prefs[MEASUREMENT_CONSTRUCTION_UUID] ?: ""

            mapOf(S to sUuid, C to cUuid, MC to mcUuid)
        }.first()
    }

    override suspend fun writeSiteData(sUuid: String, cUuid: String, mcUuid: String) {
        dataStore.edit { prefs ->
            prefs[SITE_UUID] = sUuid
            prefs[CONSTRUCTION_UUID] = cUuid
            prefs[MEASUREMENT_CONSTRUCTION_UUID] = mcUuid
        }
    }
}