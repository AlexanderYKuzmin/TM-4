package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site

import com.kuzmin.tm_4.common.util.DegreeConverter
import java.util.UUID

data class SiteParams(

    val siteUuid: String,

    val name: String,

    val description: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val siteType: Int = 0,

    val siteTypeDescription: String = "",

    val durability: String = "const"
) {
    val coordinates: String get() = DegreeConverter.doublesPairToDegreeString(latitude, longitude)
}
