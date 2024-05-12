package com.kuzmin.tm_4.feature.api.domain.model.sample

data class AddressSample(
    val country: String,

    val city: String,

    val street: String,

    val building: String,

    val region: String,

    val subRegion: String,

    val postalCode: String,
) {
    override fun toString(): String {
        return String.format(
            "%S, %S, %S, %S, %S, %S",
            country, region, subRegion, city, street, building
        )
    }
}
