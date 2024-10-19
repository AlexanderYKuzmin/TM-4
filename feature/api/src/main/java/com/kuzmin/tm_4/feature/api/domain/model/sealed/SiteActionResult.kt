package com.kuzmin.tm_4.feature.api.domain.model.sealed

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.MeasurementConstruction

sealed class SiteActionResult {
    data object DeleteTempsSuccess : SiteActionResult()

    data object Loading : SiteActionResult()

    class SuccessMc(val mc: MeasurementConstruction) : SiteActionResult()

    class SuccessMcFullList(val mcFullList: List<McFull>?) : SiteActionResult()

    class SuccessC(val constructionFull: ConstructionFull) : SiteActionResult()

    class SuccessMcAndC(val mcAndC: McAndConstruction) : SiteActionResult()

    class Error(val throwable: Throwable) : SiteActionResult()
}


