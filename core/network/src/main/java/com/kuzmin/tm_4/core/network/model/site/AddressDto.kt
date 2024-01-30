package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("country")
    val country: String,

    @SerializedName("region")
    val region : String,

    @SerializedName("region_code")
    val regionCode: Int = 0,

    @SerializedName("sub_region")
    val subRegion: String = "",

    @SerializedName("city")
    val city: String,

    @SerializedName("street")
    val street: String = "",

    @SerializedName("building")
    val building: String = "",

    @SerializedName("postal_code")
    val postalCode: String = "",
)