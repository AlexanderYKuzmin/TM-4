package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "measurements",
    indices = [
        Index(
            value = ["m_site_uuid", "m_mc_uuid"]
        )
    ]
)
data class MeasurementDb(
    @PrimaryKey(false)
    @ColumnInfo("m_uuid")
    val uuid: String,

    @ColumnInfo("m_site_uuid")
    val siteUuid: String,

    @ColumnInfo("m_mc_uuid")
    val measurementConstructionUuid: String,

    @ColumnInfo("m_group_uuid")
    val measurementGroupUuid: String,

    @ColumnInfo("m_level")
    val level: Int,

    @ColumnInfo("m_left_angle_cl")
    val leftAngleCl: Double,

    @ColumnInfo("m_left_angle_cr")
    val leftAngleCr: Double,

    @ColumnInfo("m_right_angle_cr")
    val rightAngleCr: Double,

    @ColumnInfo("m_right_angle_cl")
    val rightAngleCl: Double,
)
