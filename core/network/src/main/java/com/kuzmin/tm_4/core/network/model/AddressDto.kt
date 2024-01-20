package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("address_uuid")
    val uuid: String,

    /*@SerializedName("site_uuid")
    val siteUuid: String,*/

    @SerializedName("address_id")
    val id: Long,

    @SerializedName("country")
    val country: String,

    @SerializedName("region")
    val region : String,

    @SerializedName("sub_region")
    val subRegion: String?,

    @SerializedName("city")
    val city: String,

    @SerializedName("street")
    val street: String? = null,

    @SerializedName("building")
    val building: String? = null,

    @SerializedName("postal_code")
    val postalCode: Int? = null,

    @SerializedName("region_code")
    val regionCode: Int? = null
)