package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "site_params",
    indices = [
        Index(
            value = ["sp_site_uuid"],
            unique = true
        )
    ]
)
data class SiteParamsDb(

    @PrimaryKey(false)
    @ColumnInfo("sp_uuid")
    val uuid: String,

    @ColumnInfo("sp_site_uuid")
    val siteUuid: String,

    @ColumnInfo("sp_name")
    val name: String,

    @ColumnInfo("sp_description")
    val description: String,

    @ColumnInfo("sp_latitude")
    val latitude: Double,

    @ColumnInfo("sp_longitude")
    val longitude: Double,

    @ColumnInfo("sp_site_type")
    val siteType: Int,

    @ColumnInfo("sp_site_type_desc")
    val siteTypeDescription: String
)
