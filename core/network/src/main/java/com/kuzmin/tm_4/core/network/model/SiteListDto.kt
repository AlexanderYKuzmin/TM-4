package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SiteListDto(
    @SerializedName("sites")
    @Expose
    val sites: List<SiteDto>? = null
)