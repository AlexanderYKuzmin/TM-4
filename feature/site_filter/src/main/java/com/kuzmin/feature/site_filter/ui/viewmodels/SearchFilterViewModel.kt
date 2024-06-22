package com.kuzmin.feature.site_filter.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.feature.site_filter.domain.model.SearchFilterDataResult
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val searchFilterPrefManager: SearchFilterPrefManager
) : ViewModel() {

    private var _searchFilterDataResult = MutableLiveData<SearchFilterDataResult>()
    val searchFilterDataResult: LiveData<SearchFilterDataResult> get() = _searchFilterDataResult

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _searchFilterDataResult.postValue(SearchFilterDataResult.Error(throwable))
    }

    init {
        loadSearchFilterData()
    }

    private fun loadSearchFilterData() {
        viewModelScope.launch {
            _searchFilterDataResult.value =
                SearchFilterDataResult.Success(
                    searchFilterPrefManager.readFilterData()
                )
        }
    }

    suspend fun saveSearchData(searchFilterData: SearchFilterData) {
        Log.d("Get All", "Save Search Data")
        searchFilterPrefManager.writeFilterData(searchFilterData)

    }
}