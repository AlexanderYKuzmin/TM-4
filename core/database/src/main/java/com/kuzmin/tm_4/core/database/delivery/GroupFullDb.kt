package com.kuzmin.tm_4.core.database.delivery

import androidx.room.Embedded
import androidx.room.Relation
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb

data class GroupFullDb (
    @Embedded
    val group: GroupDb,

    @Relation(parentColumn = "gr_uuid", entityColumn = "m_group_uuid")
    val measurements: List<MeasurementDb>? = null,

    @Relation(parentColumn = "gr_uuid", entityColumn = "r_group_uuid")
    val results: List<ResultDb>? = null
)