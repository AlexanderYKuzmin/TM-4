package com.kuzmin.tm_4.feature.sites.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.SiteTinyData
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.sites.domain.model.sealed.SiteResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class SiteListViewModel(
    private val sitePrefManager: SitePrefManager
) : ViewModel() {

    protected abstract val storageLocation: Int

    protected val _siteResult = MutableLiveData<SiteResult>()
    val siteResult: LiveData<SiteResult> get() = _siteResult

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _siteResult.postValue(SiteResult.Error(throwable))
    }

    fun storeSiteData(siteTinyData: SiteTinyData) {
        viewModelScope.launch { sitePrefManager.writeSiteData(siteTinyData) }
    }

    fun observeSiteResult(lifecycleOwner: LifecycleOwner, action: (SiteResult) -> Unit) {
        siteResult.observe(lifecycleOwner) {
            Log.d("get All", "Observe. Start render Fragment.")
            action.invoke(it)
        }
    }

    abstract fun loadSiteList()

    abstract fun saveSite(site: Site)

    abstract fun deleteSite(site: Site)

}