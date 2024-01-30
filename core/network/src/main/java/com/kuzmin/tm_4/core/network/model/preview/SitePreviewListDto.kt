package com.kuzmin.tm_4.core.network.model.preview

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kuzmin.tm_4.core.network.model.preview.SiteSampleDto

data class SitePreviewListDto(
    @SerializedName("sites")
    @Expose
    val sitesPreview: List<SiteSampleDto>? = null
)
