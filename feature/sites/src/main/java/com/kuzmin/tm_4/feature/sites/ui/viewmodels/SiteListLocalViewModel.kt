package com.kuzmin.tm_4.feature.sites.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllPhotoSamplesLocalUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSiteSamplesLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SiteListLocalViewModel @Inject constructor(
    private val getAllSiteSamplesLocalUseCase: GetAllSiteSamplesLocalUseCase,
    private val getAllPhotoSamplesLocalUseCase: GetAllPhotoSamplesLocalUseCase,
    private val searchFilterPrefManager: SearchFilterPrefManager,
    sitePrefManager: SitePrefManager
) : SiteListViewModel(sitePrefManager){

    override var isFiltered: Boolean = false

    override val storageLocation: Int
        get() = STORAGE_LOCAL

    override fun loadSiteList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val searchFilter = searchFilterPrefManager.readFilterData()
            Log.d("get All", "Search filter LOCAL. ${searchFilter.toString()}")

            val sitesDeferred = async { getAllSiteSamplesLocalUseCase() }
            //val photoDeferred = async { getAllPhotoSamplesLocalUseCase() }
            Log.d("Get all", "sitesDeferred: $sitesDeferred")
            Log.d("Get all", "list samples: ${sitesDeferred.await()}")
            withContext(Dispatchers.Main) {
                _siteResult.value = SiteResult.Success(
                    /*sitesDeferred.await().map {
                        it.copy(photoUrl = photoDeferred.await().first().localPath)
                    }*/
                    sitesDeferred.await()
                )
            }
        }
    }

    override fun saveSite(site: Site) {
        TODO("Not yet implemented")
    }

    override fun deleteSite(site: Site) {
        TODO("Not yet implemented")
    }
}