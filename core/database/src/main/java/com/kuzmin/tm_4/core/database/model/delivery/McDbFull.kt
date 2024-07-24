package com.kuzmin.tm_4.core.database.model.delivery

import androidx.room.Embedded
import androidx.room.Relation
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb

data class McDbFull(

    @Embedded
    val mcDb: MeasurementConstructionDb,

    @Relation(parentColumn = "mc_uuid", entityColumn = "gr_meas_constr_uuid")
    val groups: List<GroupDb>,

    @Relation(parentColumn = "mc_uuid", entityColumn = "m_mc_uuid")
    val measurements: List<MeasurementDb>,

    @Relation(parentColumn = "mc_uuid", entityColumn = "r_mc_uuid")
    val results: List<ResultDb>,

    @Relation(parentColumn = "mc_uuid", entityColumn = "l_mc_uuid")
    val levelsInfo: List<LevelDb>
)