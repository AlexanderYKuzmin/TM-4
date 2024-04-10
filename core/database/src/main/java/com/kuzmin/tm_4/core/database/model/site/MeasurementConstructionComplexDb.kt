package com.kuzmin.tm_4.core.database.model.site

import androidx.room.Embedded
import androidx.room.Relation

data class MeasurementConstructionComplexDb(
    @Embedded
    val measurementConstructionDb: MeasurementConstructionDb,

    @Relation(parentColumn = "uuid", entityColumn = "measurement_construction_uuid")
    val groups: List<GroupDb>,

    @Relation(parentColumn = "uuid", entityColumn = "measurement_group_uuid")
    val measurements: List<MeasurementDb>,

    @Relation(parentColumn = "uuid", entityColumn = "measurement_group_uuid")
    val results: List<ResultDb>
)
