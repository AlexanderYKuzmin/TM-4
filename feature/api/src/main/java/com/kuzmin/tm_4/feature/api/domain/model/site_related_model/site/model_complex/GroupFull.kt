package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Measurement
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Result

data class GroupFull(
    val group: Group,

    val measurements: List<Measurement>,

    val results: List<Result>
)
