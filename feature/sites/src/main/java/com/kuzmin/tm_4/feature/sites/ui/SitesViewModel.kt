package com.kuzmin.tm_4.feature.sites.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.extension.isConsistentQuery
import com.kuzmin.tm_4.feature.api.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.*
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSamplesUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSitesUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByIdUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SitesViewModel @Inject constructor(
    private val getAllSitesUseCase: GetAllSitesUseCase,
    private val getSitesByNameUseCase: GetSitesByNameUseCase,
    private val getAllSamplesUseCase: GetAllSamplesUseCase,
    private val searchQuerySharedContainer: SearchQuerySharedContainer
) : ViewModel(){

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    /*private val _siteResult = MutableStateFlow<SiteResult>(Loading)
    val siteResult: StateFlow<SiteResult> get() = _siteResult*/


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //_siteResult.postValue(Error(throwable))
        _siteResult.value = Error(throwable)
    }

    fun observeQuery(context: LifecycleOwner) {
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
    }

    private fun getAll() {
        Log.d("MainActivity", "GET ALL")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            //_siteResult.value = Loading

            val sitesDeferred = async { getAllSitesUseCase() }
            val photoDeferred = async { getAllSamplesUseCase() }

            withContext(Dispatchers.Main) {
                _siteResult.value = Success(
                    sitesDeferred.await().map {
                        Log.d("getAll", "SiteSimple: $it")
                        Log.d("getAll", "PhotoUrl: ${photoDeferred.await().values}")
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

    private fun getSitesByName(name: String) {
        Log.d("MainActivity", "GET SITES BY NAMES")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val sites = getSitesByNameUseCase(name)
            withContext(Dispatchers.Main) {
                _siteResult.value = Success(sites)
            }
        }
    }
}