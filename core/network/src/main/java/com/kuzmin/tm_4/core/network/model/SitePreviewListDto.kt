package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SitePreviewListDto(
    @SerializedName("sites")
    @Expose
    val sitesPreview: List<SiteSimpleDto>? = null
)
