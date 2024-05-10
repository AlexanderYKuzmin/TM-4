package com.kuzmin.tm_4.feature.measurements.domain.model

import com.kuzmin.tm_4.feature.api.model.model_complex_obj.ConstructionFull
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction

sealed class MeasurementConstructionResult {
    data object Loading : MeasurementConstructionResult()

    class SuccessMc(val measurementConstructionList: List<MeasurementConstruction>) : MeasurementConstructionResult()

    class SuccessC(val constructionFull: ConstructionFull) : MeasurementConstructionResult()

    class Error(val throwable: Throwable) : MeasurementConstructionResult()
}


