package com.kuzmin.tm_4.feature.api.domain.model.model_complex

import com.kuzmin.tm_4.feature.api.domain.model.site.Construction

data class McAndConstruction(
    val construction: Construction,

    val mcFull: McFull
)
