package com.kuzmin.tm_4.core.network_fb.model

import com.google.firebase.firestore.PropertyName

data class MeasurementConstructionFbDto (

    var uuid: String = "",

    @get:PropertyName("start_level")
    @set:PropertyName("start_level")
    var startLevel: Int = 0,

    @get:PropertyName("measurement_name")
    @set:PropertyName("measurement_name")
    var measurementName: String = "",

    @get:PropertyName("is_completed")
    @set:PropertyName("is_completed")
    var isCompleted: Boolean = false,

    @get:PropertyName("completed_date")
    @set:PropertyName("completed_date")
    var completedDate: Long = -1L,

    @get:PropertyName("c_date")
    @set:PropertyName("c_date")
    var cDate: Long = -1L,

    @get:PropertyName("creator_name")
    @set:PropertyName("creator_name")
    var creatorName: String = "",

    @get:PropertyName("creator_uuid")
    @set:PropertyName("creator_uuid")
    var creatorUuid: String = "",

    @get:PropertyName("employee_name")
    @set:PropertyName("employee_name")
    var employeeName: String = "",

    @get:PropertyName("employee_uuid")
    @set:PropertyName("employee_uuid")
    var employeeUuid: String = "",

    @get:PropertyName("construction_uuid")
    @set:PropertyName("construction_uuid")
    var constructionUuid: String = "",

    @get:PropertyName("result_by_levels")
    @set:PropertyName("result_by_levels")
    var resultByLevels: List<Int> = emptyList(),

    @get:PropertyName("failed_levels")
    @set:PropertyName("failed_levels")
    var failedLevels: List<Boolean> = emptyList(),

    var conclusion: Boolean = false,

    @get:PropertyName("max_shift")
    @set:PropertyName("max_shift")
    var maxShift: Int = 0
)
