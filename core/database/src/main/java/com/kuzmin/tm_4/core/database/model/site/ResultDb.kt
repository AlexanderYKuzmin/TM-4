package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "results",
    indices = [
        Index(
            value = ["r_site_uuid", "r_mc_uuid"]
        ),
        Index(
            value = ["r_m_uuid"], unique = true
        )
    ]
)
data class ResultDb(
    @PrimaryKey(false)
    @ColumnInfo("r_uuid")
    val uuid: String,

    @ColumnInfo("r_site_uuid")
    val siteUuid: String,

    @ColumnInfo("r_mc_uuid")
    val measurementConstructionUuid: String,

    @ColumnInfo("r_group_uuid")
    val measurementGroupUuid: String,

    @ColumnInfo("r_m_uuid")
    val measurementUuid: String,

    @ColumnInfo("r_level")
    val level: Int,

    @ColumnInfo("r_section_uuid")
    val sectionUuid: String,

    @ColumnInfo("r_average_cl")
    val averageCl: Double,

    @ColumnInfo("r_average_cr")
    val averageCr: Double,

    @ColumnInfo("r_average_cl_cr")
    val averageClCr: Double,

    @ColumnInfo("r_shift_deg")
    val shiftDeg: Double,

    @ColumnInfo("r_shift_mm")
    val shiftMm: Int,

    @ColumnInfo("r_tan_alpha")
    val tanAlpha: Double,

    @ColumnInfo("r_dist_to_measure_level")
    val distToMeasureLevel: Int,

    @ColumnInfo("r_dist_delta")
    val distDelta: Int,

    @ColumnInfo("r_beta_average_left")
    val betaAverageLeft: Double,

    @ColumnInfo("r_beta_average_right")
    val betaAverageRight: Double,

    @ColumnInfo("r_beta_i")
    val betI: Double,

    @ColumnInfo("r_beta_delta")
    val betaDelta: Int,
)
