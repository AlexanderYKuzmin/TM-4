package com.kuzmin.tm_4.core.network.model.preview

import com.google.gson.annotations.SerializedName

data class AddressSampleDto (

    val country: String,

    val city: String,

    val street: String,

    val building: String,

    val region: String,

    @SerializedName("sub_region")
    val subRegion: String,

    @SerializedName("postal_code")
    val postalCode: String,

    @SerializedName("c_date")
    val cDate: String
)