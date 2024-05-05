package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.measurements.domain.model.MeasurementConstructionResult
import com.kuzmin.tm_4.feature.measurements.domain.usecases.GetMeasurementConstructionFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TmPagerViewModel @Inject constructor(
    private val getMeasurementConstructionFromDbUseCase: GetMeasurementConstructionFromDbUseCase
) : ViewModel(){


    private val _mcResult = MutableLiveData<MeasurementConstructionResult>()
    val mcResult: LiveData<MeasurementConstructionResult> get() = _mcResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _mcResult.postValue(MeasurementConstructionResult.Error(throwable))
    }


    fun getMeasurementConstructionFromDb(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mc = getMeasurementConstructionFromDbUseCase(mcUuid) ?: throw RuntimeException("Wrong mcUuid, there is no such mc in db.")
            _mcResult.postValue(MeasurementConstructionResult.Success(listOf(mc)))
        }
    }
}