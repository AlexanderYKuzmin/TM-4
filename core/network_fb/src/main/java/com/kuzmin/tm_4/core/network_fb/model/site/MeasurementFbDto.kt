package com.kuzmin.tm_4.core.network_fb.model.site

import com.google.firebase.firestore.PropertyName

data class MeasurementFbDto(

    var uuid: String = "",

    @get:PropertyName("group_uuid")
    @set:PropertyName("group_uuid")
    var groupUuid: String = "",

    var level: Int = -1,

    @get:PropertyName("left_angle_cl")
    @set:PropertyName("left_angle_cl")
    var leftAngleCl: Double = 0.0,

    @get:PropertyName("left_angle_cr")
    @set:PropertyName("left_angle_cr")
    var leftAngleCr: Double = 0.0,

    @get:PropertyName("right_angle_cl")
    @set:PropertyName("right_angle_cl")
    var rightAngleCl: Double = 0.0,

    @get:PropertyName("right_angle_cr")
    @set:PropertyName("right_angle_cr")
    var rightAngleCr: Double = 0.0
)