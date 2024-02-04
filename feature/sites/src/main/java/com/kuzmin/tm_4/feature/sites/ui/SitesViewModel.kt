package com.kuzmin.tm_4.feature.sites.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.extension.isConsistentQuery
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult.*
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSitesUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByIdUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SitesViewModel @Inject constructor(
    private val getAllSitesUseCase: GetAllSitesUseCase,
    private val getSitesByIdUseCase: GetSitesByIdUseCase,
    private val getSitesByNameUseCase: GetSitesByNameUseCase,
    private val searchQuerySharedContainer: SearchQuerySharedContainer
) : ViewModel(){

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
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
            _siteResult.value = Success(getAllSitesUseCase())
        }


    }

    private fun getSitesByName(name: String) {
        Log.d("MainActivity", "GET SITES BY NAMES")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _siteResult.postValue(Success(getSitesByNameUseCase(name)))
        }
    }

}