package com.kuzmin.tm_4.feature.report.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.api.ui.ParentFeatureViewModel
import com.kuzmin.tm_4.feature.report.domain.usecases.GetMcAndConstructionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportGraphsParentFeatureViewModel  @Inject constructor(
    private val getMcAndConstructionUseCase: GetMcAndConstructionUseCase
): ParentFeatureViewModel() {

    /*fun getConstructionFromDb(cUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val construction = getConstructionUseCase(cUuid)
            _mcResult.postValue(MeasurementConstructionResult.SuccessC(construction))
        }

    }*/
    /*// так тое хреново. из-за mcUUId сначала придется вытаскивать mc
    fun getFullConstructionFromDb(cUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val construction = getConstructionFullFromDbUseCase(cUuid) ?: throw RuntimeException("Wrong cUuid, there is no such construction in db.")
            _mcResult.postValue(MeasurementConstructionResult.SuccessC(construction))
        }
    }*/

    fun getMcAndConstruction(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mcAndC = getMcAndConstructionUseCase(mcUuid) ?: throw RuntimeException("Wrong cUuid, there is no such construction in db.")
            _mcResult.postValue(
                McAndCResult.SuccessMcAndC(mcAndC)
            )
        }
    }

}