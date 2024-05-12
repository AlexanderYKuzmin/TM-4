package com.kuzmin.tm_4.feature.api.domain.model.sealed

import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction

sealed class McAndCResult {
    data object Loading : McAndCResult()

    class SuccessMc(val measurementConstructionList: List<MeasurementConstruction>) : McAndCResult()

    class SuccessC(val constructionFull: ConstructionFull) : McAndCResult()

    class SuccessMcAndC(val mcAndC: McAndConstruction) : McAndCResult()

    class Error(val throwable: Throwable) : McAndCResult()
}


