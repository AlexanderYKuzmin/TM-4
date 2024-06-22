package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.common.extension.formatToDateString
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.common.util.CommonConstants.START_DATE_MILLIS_DEFAULT
import com.kuzmin.tm_4.data.local.datastore.SearchFilterScheme.CITY
import com.kuzmin.tm_4.data.local.datastore.SearchFilterScheme.END_DATE
import com.kuzmin.tm_4.data.local.datastore.SearchFilterScheme.REGION
import com.kuzmin.tm_4.data.local.datastore.SearchFilterScheme.SITE_NAME
import com.kuzmin.tm_4.data.local.datastore.SearchFilterScheme.START_DATE
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

val Context.dataStoreSearchFilter: DataStore<Preferences> by preferencesDataStore(name = "search_filter_data")

class SearchFilterPrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SearchFilterPrefManager {

    private val dataStore = appContext.dataStoreSearchFilter

    override suspend fun writeFilterData(data: SearchFilterData) {
        with(data) {
            dataStore.edit { prefs ->
                prefs[START_DATE] = dateStart.formatToDateString()
                prefs[END_DATE] = dateEnd.formatToDateString()
                prefs[REGION] = region
                prefs[CITY] = city
                prefs[SITE_NAME] = siteName
            }
        }
    }

    override suspend fun readFilterData(): SearchFilterData {
        with(SearchFilterScheme) {
            return dataStore.data.map { prefs ->
                SearchFilterData(
                    prefs[START_DATE]?.toDate() ?: Date(START_DATE_MILLIS_DEFAULT),
                    prefs[END_DATE]?.toDate() ?: Date(),
                    prefs[REGION] ?: "",
                    prefs[CITY] ?: "",
                    prefs[SITE_NAME] ?: ""
                )
            }.first()
        }
    }
}