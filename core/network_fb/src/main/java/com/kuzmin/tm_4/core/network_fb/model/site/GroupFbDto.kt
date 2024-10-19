package com.kuzmin.tm_4.core.network_fb.model.site

import com.google.firebase.firestore.PropertyName

data class GroupFbDto(
    var uuid: String = "",

    @get:PropertyName("group_num")
    @set:PropertyName("group_num")
    var groupNum: Int = 0,

    var azimuth: Int = 0,

    @get:PropertyName("theo_distance")
    @set:PropertyName("theo_distance")
    var theoDistance: Int = 0,

    @get:PropertyName("theo_height")
    @set:PropertyName("theo_height")
    var theoHeight: Int = 0,

    @get:PropertyName("mc_uuid")
    @set:PropertyName("mc_uuid")
    var mcUuid: String = ""
)