package com.kuzmin.tm_4.feature.api.domain.model.search_filter

import com.kuzmin.tm_4.common.util.CommonConstants.START_DATE_MILLIS_DEFAULT
import java.util.Date

data class SearchFilterData(
    val dateStart: Date = Date(START_DATE_MILLIS_DEFAULT),

    val dateEnd: Date = Date(),

    val region: String = "",

    val city: String = "",

    val siteName: String = ""
)
