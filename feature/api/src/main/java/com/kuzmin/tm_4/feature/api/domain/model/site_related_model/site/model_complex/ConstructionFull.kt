package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex

data class ConstructionFull(
    val constructionAndSections: ConstructionAndSections,

    val measurementConstructionFullList: List<McFull>
)