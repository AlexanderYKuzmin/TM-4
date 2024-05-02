package com.kuzmin.tm_4.feature.api.model.site

data class FullConstruction(
    val construction: Construction,

    val measurementConstruction: MeasurementConstruction,

    val sections: List<Section>,

    val measureGroups: List<Group>,

    val measurements: List<Measurement>? = null,

    val results: List<Result>? = null
)
