package com.kuzmin.tm_4.feature.sites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_SERVER
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import com.kuzmin.tm_4.feature.sites.domain.usecases.GetSitesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class SiteViewModel @Inject constructor(
    private val getSiteByIdUseCase: GetSitesByIdUseCase
) : ViewModel() {
    private var siteLocalId: Long? = null

    private val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        _siteResult.postValue(SiteResult.Error(throwable))
    }

    fun getSiteById(id: Long?, storage: Int?) {
        if (id == null || storage == null) return
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val sites = if (storage == STORAGE_SERVER) {
                getSiteByIdUseCase(listOf(id))
            } else {
                TODO()
            }
            withContext(Dispatchers.Main) {
                _siteResult.value = SiteResult.SuccessSingle(sites.first())
            }
        }
    }
}