package com.kuzmin.tm_4.feature.sites.domain.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class SearchQuerySharedContainer @Inject constructor(
    private val searchQuery: MutableLiveData<String>
) {

    fun setData(query: String) {
        Log.d("MainActivity", "Search container set query")
        Log.d("MainActivity", "Live Data in SearchContainer: $searchQuery")
        searchQuery.value = query
    }

    fun getData(): LiveData<String> {
        Log.d("MainActivity", "Search container get data")
        return searchQuery
    }
}