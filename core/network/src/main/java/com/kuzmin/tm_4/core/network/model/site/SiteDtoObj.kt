package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzmin.tm_4.core.network.model.preview.SiteSampleDto

class SiteDtoObj {
    @SerializedName("sites")
    @Expose
    val sitesDto: List<SiteDto>? = null
}