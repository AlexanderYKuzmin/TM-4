package com.kuzmin.tm_4.feature.sites.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.SAMPLE_PHOTO_NAME_SUFFIX_SERVER
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllPhotoSamplesRemoteUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetAllSiteSamplesRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SiteListRemoteViewModel @Inject constructor(
    private val getAllSiteSamplesRemoteUseCase: GetAllSiteSamplesRemoteUseCase,
    private val getAllPhotoSamplesRemoteUseCase: GetAllPhotoSamplesRemoteUseCase,
    private val searchFilterPrefManager: SearchFilterPrefManager,
    sitePrefManager: SitePrefManager
    ) : SiteListViewModel(sitePrefManager) {

    override val storageLocation: Int
        get() = STORAGE_REMOTE

    override fun loadSiteList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val searchFilter = searchFilterPrefManager.readFilterData()
            Log.d("get All", "Search filter. $searchFilter")

            val sitesDeferred = async { getAllSiteSamplesRemoteUseCase() }
            val photoDeferred = async { getAllPhotoSamplesRemoteUseCase() }

            withContext(Dispatchers.Main) {
                _siteResult.value = SiteResult.Success(
                    sitesDeferred.await().map {
                        Log.d("get All", "Site Sample. $it")
                        it.copy(photoUrl = photoDeferred.await()[it.uuid + SAMPLE_PHOTO_NAME_SUFFIX_SERVER])
                    }
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