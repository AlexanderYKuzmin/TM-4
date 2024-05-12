package com.kuzmin.tm_4.feature.api.domain.model.model_complex_obj

import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction

data class McAndConstruction(
    val construction: Construction,

    val mcFull: McFull
)
