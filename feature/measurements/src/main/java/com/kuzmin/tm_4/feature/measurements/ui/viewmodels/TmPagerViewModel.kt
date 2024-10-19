package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.SiteActionResult
import com.kuzmin.tm_4.feature.api.domain.usecases.GetMeasurementConstructionFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TmPagerViewModel @Inject constructor(
    private val getMeasurementConstructionFromDbUseCase: GetMeasurementConstructionFromDbUseCase
) : ViewModel(){

    private val _mcResult = MutableLiveData<SiteActionResult>()
    val mcResult: LiveData<SiteActionResult> get() = _mcResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _mcResult.postValue(SiteActionResult.Error(throwable))
    }


    fun getMeasurementConstructionFromDb(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mc = getMeasurementConstructionFromDbUseCase(mcUuid) ?: throw RuntimeException("Wrong mcUuid, there is no such mc in db.")
            _mcResult.postValue(SiteActionResult.SuccessMc(mc))
        }
    }
}