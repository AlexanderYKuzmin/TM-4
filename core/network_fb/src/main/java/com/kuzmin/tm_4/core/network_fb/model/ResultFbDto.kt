package com.kuzmin.tm_4.core.network_fb.model

import com.google.firebase.firestore.PropertyName

data class ResultFbDto(

    var uuid: String = "",

    @get:PropertyName("measurement_uuid")
    @set:PropertyName("measurement_uuid")
    var measurementUuid: String = "",

    @get:PropertyName("group_uuid")
    @set:PropertyName("group_uuid")
    var groupUuid: String = "",

    @get:PropertyName("average_cl")
    @set:PropertyName("average_cl")
    var averageCl: Double = 0.0,

    @get:PropertyName("average_cr")
    @set:PropertyName("average_cr")
    var averageCr: Double = 0.0,

    @get:PropertyName("average_cl_cr")
    @set:PropertyName("average_cl_cr")
    var averageClCr: Double = 0.0,

    @get:PropertyName("shift_deg")
    @set:PropertyName("shift_deg")
    var shiftDeg: Double = 0.0,

    @get:PropertyName("shift_mm")
    @set:PropertyName("shift_mm")
    var shiftMm: Int = 0,

    @get:PropertyName("tan_alfa")
    @set:PropertyName("tan_alfa")
    var tanAlfa: Double = 0.0,

    var level: Int = -1,

    @get:PropertyName("dist_to_measure_level")
    @set:PropertyName("dist_to_measure_level")
    var distToMeasureLevel: Int = 0,

    @get:PropertyName("dist_delta")
    @set:PropertyName("dist_delta")
    var distDelta: Int = 0,

    @get:PropertyName("beta_average_left")
    @set:PropertyName("beta_average_left")
    var betaAverageLeft: Double = 0.0,

    @get:PropertyName("beta_average_right")
    @set:PropertyName("beta_average_right")
    var betaAverageRight: Double = 0.0,

    @get:PropertyName("beta_i")
    @set:PropertyName("beta_i")
    var betaI: Double = 0.0,

    @get:PropertyName("beta_delta")
    @set:PropertyName("beta_delta")
    var betaDelta: Int = 0
)