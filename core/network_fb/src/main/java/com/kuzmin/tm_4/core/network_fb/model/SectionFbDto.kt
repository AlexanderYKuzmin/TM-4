package com.kuzmin.tm_4.core.network_fb.model

import com.google.firebase.firestore.PropertyName

data class SectionFbDto(

    var uuid: String = "",

    @get:PropertyName("c_date")
    @set:PropertyName("c_date")
    var cDate: String = "",

    var height: Int = -1,

    var number: Int = -1,

    @get:PropertyName("w_bottom")
    @set:PropertyName("w_bottom")
    var wBottom: Int = -1,

    @get:PropertyName("w_top")
    @set:PropertyName("w_top")
    var wTop: Int = -1,

    var status: String = "",

    @get:PropertyName("construction_uuid")
    @set:PropertyName("construction_uuid")
    var constructionUuid: String = ""
)