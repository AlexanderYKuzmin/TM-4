package com.kuzmin.tm_4.feature.site_creation.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiteCreationStateData(
    val siteName: String = "",
    val siteDescription: String = "",
    val type: String = "",
    val typeDescription: String = "",
    val owner: String = "",

    val country: String = "",
    val region: String = "",
    val subRegion: String = "",
    val city: String = "",
    val street: String = "",
    val building: String = "",
    val postalCode: String = "",
    val regionCode: String = "",

    val latitude: String = "",
    val longitude: String = "",
) : Parcelable
