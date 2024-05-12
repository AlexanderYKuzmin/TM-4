package com.kuzmin.tm_4.feature.report.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.api.domain.model.sealed.McAndCResult
import com.kuzmin.tm_4.feature.api.ui.ParentFeatureViewModel
import com.kuzmin.tm_4.feature.report.domain.usecases.GetMcAndConstructionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class ItemGraphViewModel @Inject constructor(
    private val getMcAndConstructionUseCase: GetMcAndConstructionUseCase
) : ParentFeatureViewModel() {

    fun getMcAndConstruction(mcUuid: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val mcAndC = getMcAndConstructionUseCase(mcUuid) ?: throw IllegalStateException("Incorrect mc UUID")

            _mcResult.postValue(McAndCResult.SuccessMcAndC(mcAndC))
        }
    }
}