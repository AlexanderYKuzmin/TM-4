package com.kuzmin.tm_4.feature.measurements.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.api.model.SiteDataStore
import com.kuzmin.tm_4.feature.api.model.usecases.GetSiteByIdFullUseCase
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult.*
import com.kuzmin.tm_4.feature.measurements.domain.usecases.GetMeasurementConstructionsBySiteIdUseCase
import com.kuzmin.tm_4.feature.measurements.domain.usecases.SaveSiteToDbUseCase
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
    private val getSiteByIdUseCase: GetSiteByIdFullUseCase,
    private val saveSiteToDbUseCase: SaveSiteToDbUseCase,
    private val sitePrefManager: SitePrefManager
) : ViewModel() {

    private var siteDataStore: SiteDataStore? = null

    private val _measurementConstructionResult = MutableLiveData<MeasurementConstructionResult>()
    val measurementConstructionResult: LiveData<MeasurementConstructionResult> get() = _measurementConstructionResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _measurementConstructionResult.value = Error(throwable)
    }

    private val getAndSaveFullSiteExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //TODO()
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

            val mcList =
                with(siteDataStore!!) {
                    getMeasurementConstructionsBySiteIdUseCase(sUuid, cUuid)
                }

            withContext(Dispatchers.Main) {
                _measurementConstructionResult.value = Success(mcList)
            }
        }
    }

     fun getAndSaveFullSiteToDbAsTemp() {
         viewModelScope.launch(Dispatchers.IO + getAndSaveFullSiteExceptionHandler) {
             val site = async {
                getSiteByIdUseCase.invoke(siteDataStore!!.sUuid)
             }.await()

            saveSiteToDbUseCase(site, CommonConstants.TEMP)
         }

    }
}