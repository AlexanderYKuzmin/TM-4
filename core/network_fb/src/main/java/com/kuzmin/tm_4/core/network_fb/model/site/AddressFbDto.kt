package com.kuzmin.tm_4.core.network_fb.model.site

import com.google.firebase.firestore.PropertyName

data class AddressFbDto(

    var uuid: String = "",

    var country: String = "РФ",

    var region: String ="",

    @get:PropertyName("sub_region")
    @set:PropertyName("sub_region")
    var subRegion: String = "",

    var city: String = "",

    var street: String = "",

    var building: String = "",

    @get:PropertyName("postal_code")
    @set:PropertyName("postal_code")
    var postalCode: String = "",

    @get:PropertyName("region_code")
    @set:PropertyName("region_code")
    var regionCode: String = "",
)
