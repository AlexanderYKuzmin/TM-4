package com.kuzmin.tm_4.feature.api.model.model_complex_obj

data class ConstructionFull(
    val constructionAndSections: ConstructionAndSections,

    val measurementConstructionFullList: List<McFull>
)