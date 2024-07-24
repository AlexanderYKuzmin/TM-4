package com.kuzmin.tm_4.feature.api.domain.model.model_complex

import com.kuzmin.tm_4.feature.api.domain.model.site.McLevelInfo
import com.kuzmin.tm_4.feature.api.domain.model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site.Measurement
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.Result

data class McFull(
    val mc: MeasurementConstruction,

    val groups: List<Group>?,

    val measurements: List<Measurement>?,

    val results: List<Result>?,

    val levelsInfo: List<McLevelInfo>? = null
)