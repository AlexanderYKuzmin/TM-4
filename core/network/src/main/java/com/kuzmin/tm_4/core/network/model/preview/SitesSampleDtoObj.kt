package com.kuzmin.tm_4.core.network.model.preview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SitesSampleDtoObj (
    @SerializedName("sites")
    @Expose
    val sitesSampleDto: List<SiteSampleDto>? = null
)