package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

val Context.dataStoreSearchFilter: DataStore<Preferences> by preferencesDataStore(name = "search_filter_data")

class SearchFilterPrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : SearchFilterPrefManager {

    private val dataStore = appContext.dataStoreSite

    override fun writeFilterData(data: SearchFilterData) {
        TODO("Not yet implemented")
    }

    override fun readFilterData(): SearchFilterData {
        TODO("Not yet implemented")
    }
}