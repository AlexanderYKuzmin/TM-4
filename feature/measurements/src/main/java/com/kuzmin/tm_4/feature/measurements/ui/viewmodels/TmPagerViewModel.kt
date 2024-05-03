package com.kuzmin.tm_4.feature.measurements.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.tm_4.feature.measurements.domain.usecases.GetMeasurementConstructionFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TmPagerViewModel @Inject constructor(
    private val getMeasurementConstructionFromDbUseCase: GetMeasurementConstructionFromDbUseCase
) : ViewModel(){


    fun getSiteFromDb() {
        viewModelScope.launch {
            getMeasurementConstructionFromDbUseCase("52528cda-1bce-419e-937e-223d1953fc0b")
        }
    }
}