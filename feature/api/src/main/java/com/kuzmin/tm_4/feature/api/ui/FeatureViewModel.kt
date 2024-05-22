package com.kuzmin.tm_4.feature.api.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class FeatureViewModel (
) : ViewModel() {
    val _mcResult = MutableLiveData<McAndCResult>()
    val mcResult: LiveData<McAndCResult> get() = _mcResult

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _mcResult.postValue(McAndCResult.Error(throwable))
    }


    /*fun getMeasurementConstructionFromDb(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mc = getMeasurementConstructionFromDbUseCase(mcUuid) ?: throw RuntimeException("Wrong mcUuid, there is no such mc in db.")
            _mcResult.postValue(ConMcResult.SuccessMc(listOf(mc)))
        }
    }*/
}