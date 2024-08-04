package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.CONSTRUCTION_UUID
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.MEASUREMENT_CONSTRUCTION_UUID
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.SITE_NAME
import com.kuzmin.tm_4.data.local.datastore.SiteScheme.SITE_UUID
import com.kuzmin.tm_4.feature.api.domain.model.SiteTinyData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStoreSite: DataStore<Preferences> by preferencesDataStore(name = "site_data")

class SitePrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SitePrefManager{
    private val dataStore = appContext.dataStoreSite

    override suspend fun readSiteData(): SiteTinyData {
        return dataStore.data.map { prefs ->
            val sUuid = prefs[SITE_UUID] ?: ""
            val sName = prefs[SITE_NAME] ?: ""
            val cUuid = prefs[CONSTRUCTION_UUID] ?: ""
            val mcUuid = prefs[MEASUREMENT_CONSTRUCTION_UUID] ?: ""

            SiteTinyData(sUuid, sName, cUuid, mcUuid)
        }.first()
    }

    override suspend fun writeSiteData(siteTinyData: SiteTinyData) {
        with(siteTinyData) {
            dataStore.edit { prefs ->
                prefs[SITE_UUID] = sUuid
                prefs[SITE_NAME] = sName
                prefs[CONSTRUCTION_UUID] = cUuid
                prefs[MEASUREMENT_CONSTRUCTION_UUID] = mcUuid
            }
        }
    }

    override suspend fun writeMcUuid(mcUuid: String) {
        dataStore.edit { prefs ->
            prefs[MEASUREMENT_CONSTRUCTION_UUID] = mcUuid
        }
    }
}