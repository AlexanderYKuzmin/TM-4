package com.kuzmin.tm_4.feature.sites.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_SERVER
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.SiteDataStore
import com.kuzmin.tm_4.feature.api.domain.model.search_filter.SearchFilterData
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.*
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSamplesUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSitesUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SitesNavGraphViewModel @Inject constructor(
    private val getAllSitesUseCase: GetAllSitesUseCase,
    private val getSitesByNameUseCase: GetSitesByNameUseCase,
    private val getAllSamplesUseCase: GetAllSamplesUseCase,
    private val searchQuerySharedContainer: SearchQuerySharedContainer,
    private val sitePrefManager: SitePrefManager,
    private val searchFilterPrefManager: SearchFilterPrefManager
) : ViewModel(){

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    /*private val _siteResult = MutableStateFlow<SiteResult>(Loading)
    val siteResult: StateFlow<SiteResult> get() = _siteResult*/


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //_siteResult.postValue(Error(throwable))
        _siteResult.value = Error(throwable)
    }

    fun storeSiteData(siteDataStore: SiteDataStore) {
        viewModelScope.launch { sitePrefManager.writeSiteData(siteDataStore) }
    }

    fun saveSiteToDb(site: Site) {
        TODO()
    }

    fun loadSites(storage: Int) {
        Log.d("get All", "Load sites.")
        viewModelScope.launch {
            val searchFilter = searchFilterPrefManager.readFilterData()
            Log.d("get All", "Search filter. ${searchFilter.toString()}")
            when(storage) {
                STORAGE_SERVER -> {
                    //if (searchFilter.isEmpty()) getAllServer() else getSitesByFilterServer(searchFilter)
                    getAllServer()
                }
                STORAGE_LOCAL -> {
                    if (searchFilter.isEmpty()) getAllLocal() else getSitesByFilterLocal(searchFilter)
                }
            }
        }
    }

    /*fun observeQuery(context: LifecycleOwner) {
        Log.d("MainActivity", "Search container: $searchQuerySharedContainer")
        Log.d("MainActivity", "Search container liveData: ${searchQuerySharedContainer.getData()}")
        searchQuerySharedContainer.getData().observe(context) {
            Log.d("MainActivity", "Sites view model query has changed")
            if (it.isEmpty()) {
                getAll()
            } else if (it.isConsistentQuery()) {
                getSitesByName(it)
            }
        }
    }*/

    private fun getAllServer() {
        Log.d("MainActivity", "GET ALL")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            //_siteResult.value = Loading

            val sitesDeferred = async { getAllSitesUseCase() }
            val photoDeferred = async { getAllSamplesUseCase() }

            withContext(Dispatchers.Main) {
                _siteResult.value = Success(
                    sitesDeferred.await().map {
                        it.copy(photoUrl = photoDeferred.await()[it.uuid + "_s.jpg"])
                    }
                )

                /*getAllSitesUseCase()
                    .map { siteList ->
                        Log.d("getAll", "View model sitelist size: ${siteList.size}")
                        Success(
                            siteList.map { it.copy(photoUrl = photoDeferred.await()[it.uuid + "_s.jpg"]) }
                        )
                    }
                    .collect { _siteResult.postValue(it) }*/
            }
        }
    }

    private fun getAllLocal() {
        Log.d("Get All", "Get All Local")
    }

    /*private fun getSitesByName(name: String) {
        Log.d("MainActivity", "GET SITES BY NAMES")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val sites = getSitesByNameUseCase(name)
            withContext(Dispatchers.Main) {
                _siteResult.value = Success(sites)
            }
        }
    }*/

    private fun getSitesByFilterServer(searchFilter: SearchFilterData) {
        Log.d("Get All", "Get sites by filter server")
    }

    private fun getSitesByFilterLocal(searchFilter: SearchFilterData) {
        Log.d("Get All", "Get sites by filter Local")
    }
}