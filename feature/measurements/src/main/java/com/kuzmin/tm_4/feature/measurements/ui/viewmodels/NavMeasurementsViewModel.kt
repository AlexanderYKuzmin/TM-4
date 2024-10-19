package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.SiteTinyData
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.api.domain.usecases.GetAllMcFullFromDbUseCase
import com.kuzmin.tm_4.feature.api.domain.usecases.GetSiteByIdFullUseCase
import com.kuzmin.tm_4.feature.api.domain.usecases.SaveSiteToDbUseCase
import com.kuzmin.tm_4.feature.measurements.domain.usecases.GetMeasurementConstructionsBySiteIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NavMeasurementsViewModel @Inject constructor(
    private val getMeasurementConstructionsBySiteIdUseCase: GetMeasurementConstructionsBySiteIdUseCase,
    private val getAllMcFullFromDbUseCase: GetAllMcFullFromDbUseCase,
    private val getSiteByIdUseCase: GetSiteByIdFullUseCase,
    private val saveSiteToDbUseCase: SaveSiteToDbUseCase,
    private val sitePrefManager: SitePrefManager
) : ViewModel() {

    private var siteTinyData: SiteTinyData? = null

    private val _siteActionResult = MutableLiveData<SiteActionResult>()
    val siteActionResult: LiveData<SiteActionResult> get() = _siteActionResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _siteActionResult.value = SiteActionResult.Error(throwable)
    }

    private val getAndSaveFullSiteExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MC", "Got an error during get and save site: $throwable")
    }

    init {
        viewModelScope.launch {
            siteTinyData = sitePrefManager.readSiteData()
            delay(50)
        }
    }

    fun getAllMeasurementConstructions() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            if (siteTinyData == null) delay(100)

            val mcList = async {
                with(siteTinyData!!) {
                    //getMeasurementConstructionsBySiteIdUseCase(sUuid, cUuid)
                    getAllMcFullFromDbUseCase(sUuid, cUuid)
                }
            }.await()

            mcList?.forEach { Log.d("mc", "${it.levelsInfo} :: ${it.results}") }
            withContext(Dispatchers.Main) {
                _siteActionResult.value = SiteActionResult.SuccessMcFullList(mcList)
            }
        }
    }

     fun getAndSaveFullSiteToDbAsTemp() {
         viewModelScope.launch(Dispatchers.IO + getAndSaveFullSiteExceptionHandler) {
             val site = async {
                getSiteByIdUseCase.invoke(siteTinyData!!.sUuid)
             }.await()

            saveSiteToDbUseCase(site, CommonConstants.TEMP)
         }
    }

    fun saveMcUuidToDatastore(mcUuid: String) {
        viewModelScope.launch {
            sitePrefManager.writeMcUuid(mcUuid)
        }
    }
}