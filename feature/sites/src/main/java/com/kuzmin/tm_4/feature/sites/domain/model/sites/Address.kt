package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class Address(
    val uuid: String,

    val country: String,

    val region : String,

    val regionCode: Int = 0,

    val subRegion: String = "",

    val city: String,

    val street: String = "",

    val building: String = "",

    val postalCode: String = "",
)
