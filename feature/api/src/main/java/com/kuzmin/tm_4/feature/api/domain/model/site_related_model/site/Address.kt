package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site

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
) {
    override fun toString(): String {
        return String.format("%s, %s, %s, %s, %s, %s, %s", country, region, subRegion, city, street, building, postalCode)
    }
}
