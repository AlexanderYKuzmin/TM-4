package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.domain.model.SiteDataStore
import com.kuzmin.tm_4.feature.api.domain.usecases.GetSiteByIdFullUseCase
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult.*
import com.kuzmin.tm_4.feature.api.domain.usecases.GetAllMcFullFromDbUseCase
import com.kuzmin.tm_4.feature.measurements.domain.usecases.GetMeasurementConstructionsBySiteIdUseCase
import com.kuzmin.tm_4.feature.api.domain.usecases.SaveSiteToDbUseCase
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

    private var siteDataStore: SiteDataStore? = null

    private val _McAndCResult = MutableLiveData<McAndCResult>()
    val mcAndCResult: LiveData<McAndCResult> get() = _McAndCResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _McAndCResult.value = Error(throwable)
    }

    private val getAndSaveFullSiteExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MC", "Got an error during get and save site: $throwable")
    }

    init {
        viewModelScope.launch {
            siteDataStore = sitePrefManager.readSiteData()
            delay(50)
        }
    }

    fun getAllMeasurementConstructions() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            if (siteDataStore == null) delay(100)

            val mcList = async {
                with(siteDataStore!!) {
                    //getMeasurementConstructionsBySiteIdUseCase(sUuid, cUuid)
                    getAllMcFullFromDbUseCase(sUuid, cUuid)
                }
            }.await()

            Log.d("mc", "Nav meas view model. mcList:")
            mcList?.forEach { Log.d("mc", "${it.levelsInfo} :: ${it.results}") }
            withContext(Dispatchers.Main) {
                _McAndCResult.value = SuccessMcFullList(mcList)
            }
        }
    }

     fun getAndSaveFullSiteToDbAsTemp() {
         Log.d("MC", "Measurement View model. Get full site from firestore and save site to db.")
         viewModelScope.launch(Dispatchers.IO + getAndSaveFullSiteExceptionHandler) {
             val site = async {
                getSiteByIdUseCase.invoke(siteDataStore!!.sUuid)
             }.await()

             Log.d("MC", "Get site. Site: $site")
            saveSiteToDbUseCase(site, CommonConstants.TEMP)
         }
    }

    fun saveMcUuidToDatastore(mcUuid: String) {
        viewModelScope.launch {
            sitePrefManager.writeMcUuid(mcUuid)
        }
    }
}