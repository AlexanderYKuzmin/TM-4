package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "measurements_constructions",
    indices = [
        Index(
            value = ["mc_site_uuid"]
        ),
        Index(
            value = ["mc_constr_uuid"], unique = true
        )
    ]
)
data class MeasurementConstructionDb(

    @PrimaryKey(false)
    @ColumnInfo("mc_uuid")
    val uuid: String,

    @ColumnInfo("mc_site_uuid")
    val siteUuid: String,

    @ColumnInfo("mc_constr_uuid")
    val constructionUuid: String,

    @ColumnInfo("mc_measure_name")
    val measurementName: String?,

    @ColumnInfo("mc_creator_id")
    val creatorUuid: String,

    @ColumnInfo("mc_start_level_mm")
    val startLevel: Int,

    @ColumnInfo("mc_cr_date")
    val creationDate: String,

    @ColumnInfo("mc_compl_date")
    val completedDate: String,

    @ColumnInfo("mc_is_completed")
    val isCompleted: Boolean,

    @ColumnInfo("employee")
    val employeeUuid: String,

    @ColumnInfo("mc_empl_name")
    val employeeName: String,

    @ColumnInfo("mc_cr_name")
    val creatorName: String,
)
