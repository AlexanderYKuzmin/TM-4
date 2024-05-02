package com.kuzmin.tm_4.feature.measurements.domain.model

import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction

sealed class MeasurementConstructionResult {
    data object Loading : MeasurementConstructionResult()

    class Success(val measurementConstructionList: List<MeasurementConstruction>) : MeasurementConstructionResult()

    class Error(val throwable: Throwable) : MeasurementConstructionResult()
}


