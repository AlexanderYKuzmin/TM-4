package com.kuzmin.tm_4.feature.api.model.model_complex_obj

import com.kuzmin.tm_4.feature.api.model.site.Construction
import com.kuzmin.tm_4.feature.api.model.site.Section

data class ConstructionAndSections(
    val construction: Construction,

    val sections: List<Section>
)
