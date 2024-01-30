package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class SiteParamsDto (
   val id: Long,

   val name: String,

   @SerializedName("site_uuid")
   val siteUuid: String,

   val description: String = "",

   val latitude: Double = 0.0,

   val longitude: Double = 0.0,

   @SerializedName("site_type")
   val siteType: Int = 0,

   @SerializedName("site_type_description")
   val siteTypeDescription: String = ""
)