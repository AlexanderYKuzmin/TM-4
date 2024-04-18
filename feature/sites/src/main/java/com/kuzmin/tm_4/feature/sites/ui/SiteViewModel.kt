package com.kuzmin.tm_4.feature.sites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_SERVER
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetRemoteSiteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor(
    private val getSiteByIdUseCase: GetRemoteSiteByIdUseCase
) : ViewModel() {
    //private var siteLocalId: Long? = null

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        _siteResult.postValue(SiteResult.Error(throwable))
    }

    fun getSiteById(uuid: String?, storage: Int?) {
        if (uuid == null || storage == null) return
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val site = if (storage == STORAGE_SERVER) {
                getSiteByIdUseCase(uuid)
            } else {
                TODO()
            }
            withContext(Dispatchers.Main) {
                _siteResult.value = SiteResult.SuccessSingle(site)
            }
        }
    }
}