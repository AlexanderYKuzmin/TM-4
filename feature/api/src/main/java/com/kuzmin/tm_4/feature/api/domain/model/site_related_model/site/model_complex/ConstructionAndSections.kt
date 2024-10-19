package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Section

data class ConstructionAndSections(
    val construction: Construction,

    val sections: List<Section>
)
