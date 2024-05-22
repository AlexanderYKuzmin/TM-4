package com.kuzmin.tm_4.feature.report.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.api.ui.FeatureViewModel
import com.kuzmin.tm_4.feature.api.domain.usecases.GetMcAndConstructionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ReportFeatureViewModel (
    private val getMcAndConstructionUseCase: GetMcAndConstructionUseCase
): FeatureViewModel() {

    fun getMcAndConstruction(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mcAndC = getMcAndConstructionUseCase(mcUuid) ?: throw RuntimeException("Wrong cUuid, there is no such construction in db.")
            _mcResult.postValue(
                McAndCResult.SuccessMcAndC(mcAndC)
            )
        }
    }
}