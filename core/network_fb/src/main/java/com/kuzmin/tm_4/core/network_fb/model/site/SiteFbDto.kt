package com.kuzmin.tm_4.core.network_fb.model.site

import com.google.firebase.firestore.PropertyName

data class SiteFbDto(

    @get:PropertyName("site_uuid")
    @set:PropertyName("site_uuid")
    var uuid: String = "",

    @get:PropertyName("site_name")
    @set:PropertyName("site_name")
    var name: String = "",

    @get:PropertyName("site_description")
    @set:PropertyName("site_description")
    var description: String = "",

    @get:PropertyName("site_type")
    @set:PropertyName("site_type")
    var type: String = "",

    @get:PropertyName("site_type_description")
    @set:PropertyName("site_type_description")
    var typeDescription: String = "",

    @get:PropertyName("tenant_name")
    @set:PropertyName("tenant_name")
    var tenantName: String = "",

    var address: AddressFbDto = AddressFbDto(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    ),

    var geo: GeoFbDto = GeoFbDto(0.0, 0.0)
)
