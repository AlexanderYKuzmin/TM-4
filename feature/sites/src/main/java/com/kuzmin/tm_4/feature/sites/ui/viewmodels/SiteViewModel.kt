package com.kuzmin.tm_4.feature.sites.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_REMOTE
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetRemoteSiteByIdNoSectionsUseCase
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitePhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor(
    private val getSiteByIdNoSectionsUseCase: GetRemoteSiteByIdNoSectionsUseCase,
    private val getSitePhotosUseCase: GetSitePhotosUseCase
) : ViewModel() {
    //private var siteLocalId: Long? = null

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        _siteResult.postValue(SiteResult.Error(throwable))
    }

    fun getSiteByIdNoSections(sUuid: String?, cUuid: String?, storage: Int?) {
        if (sUuid == null || storage == null || cUuid == null) return
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val siteDeferred: Deferred<Site>
            val photosDeferred: Deferred<List<Photo>>

            if (storage == STORAGE_REMOTE) {
                siteDeferred = async { getSiteByIdNoSectionsUseCase(sUuid, cUuid) }
                photosDeferred = async { getSitePhotosUseCase(sUuid) }
            } else {
                TODO()
            }
            withContext(Dispatchers.Main) {
                _siteResult.value = SiteResult.SuccessSingle(
                    siteDeferred.await().copy(
                        photos = photosDeferred.await()
                    )
                )
            }
        }
    }
}