package com.kuzmin.tm_4.feature.report.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavReportViewModel @Inject constructor(
    private val sitePrefManager: SitePrefManager
) : ViewModel() {

    private val _mcUuidLiveData = MutableLiveData<String?>()
    val mcUuidLiveData: LiveData<String?> get() = _mcUuidLiveData

    fun getMcUuidFromDatastore() {
        Log.d("report", "Get mc and c. Nav Report View model. Get UUID from datastore")
        viewModelScope.launch {
            _mcUuidLiveData.value = sitePrefManager.readSiteData().mcUuid
        }
    }
}