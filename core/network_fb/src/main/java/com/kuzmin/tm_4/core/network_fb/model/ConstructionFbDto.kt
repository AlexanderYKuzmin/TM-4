package com.kuzmin.tm_4.core.network_fb.model

import com.google.firebase.firestore.PropertyName

data class ConstructionFbDto(
    var uuid: String = "",

    var type: String = "",

    @get:PropertyName("height_mm")
    @set:PropertyName("height_mm")
    var height: Int = -1,

    var config: String = "",

    @get:PropertyName("q_sections")
    @set:PropertyName("q_sections")
    var qSections: Int = -1,

    @get:PropertyName("q_levels")
    @set:PropertyName("q_levels")
    var qLevels: Int = -1,

    var description: String = "",

    var status: String = "",

    @get:PropertyName("is_measured")
    @set:PropertyName("is_measured")
    var isMeasured: Boolean = false,

    @get:PropertyName("c_date")
    @set:PropertyName("c_date")
    var cDate: String = "",

    @get:PropertyName("m_date")
    @set:PropertyName("m_date")
    var mDate: String = "",

    var version: Int = -1
)
