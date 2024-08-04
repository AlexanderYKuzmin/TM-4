package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "constructions",
    indices = [
        Index(
            value = ["constr_site_uuid"]
        )
    ]
)
data class ConstructionDb(

    @PrimaryKey(false)
    @ColumnInfo("constr_uuid")
    val uuid: String,

    @ColumnInfo("constr_version")
    val version: Int,

    @ColumnInfo("constr_desc")
    val description: String,

    @ColumnInfo("constr_status")
    val status: String,

    @ColumnInfo("constr_num_of_secs")
    val numOfSections: Int,

    @ColumnInfo("constr_height_mm")
    val height: Int,

    @ColumnInfo("constr_type")
    val constructionType: String,

    @ColumnInfo("constr_config")
    val config: String,

    @ColumnInfo("constr_levels")
    val measureLevels: Int?,

    @ColumnInfo("constr_site_uuid")
    val siteUuid: String,

    @ColumnInfo("constr_c_date")
    val cDate: String
)