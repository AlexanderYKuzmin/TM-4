package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("photo_id")
    val id: Long,

    @SerializedName("photo_uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("c_date")
    val cDate: String,

    @SerializedName("creation_date")
    val date: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("url_thumbnail")
    val urlThumbnail: String,

    @SerializedName("employee_id")
    val employeeId: Long,

    @SerializedName("employee_name")
    val employeeName: String,

    @SerializedName("foto_dimensions")
    val dimensions: String,

    @SerializedName("thumbnail_dimensions")
    val thumbnailDim: String
)