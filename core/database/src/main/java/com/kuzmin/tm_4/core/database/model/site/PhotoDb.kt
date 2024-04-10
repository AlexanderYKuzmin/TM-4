package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "photos",
    indices = [
        Index(
            value = ["ph_site_uuid"]
        )
    ]
)
data class PhotoDb(
    @PrimaryKey(false)
    @ColumnInfo("ph_uuid")
    val uuid: String,

    @ColumnInfo("ph_site_uuid")
    val siteUuid: String,

    @ColumnInfo("ph_name")
    val name: String,

    @ColumnInfo("ph_cr_date")
    val date: String,

    @ColumnInfo("ph_url")
    val url: String,

    @ColumnInfo("ph_url_thumb")
    val urlThumbnail: String,

    @ColumnInfo("ph_employee_id")
    val employeeId: Long,

    @ColumnInfo("ph_employee_name")
    val employeeName: String,

    @ColumnInfo("ph_dimen")
    val dimensions: String,

    @ColumnInfo("ph_thumb_dimen")
    val thumbnailDim: String?,
)
