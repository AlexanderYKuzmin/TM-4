package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tenants",
    indices = [
        Index(
            value = ["ten_site_uuid"]
        )
    ]
)
data class TenantDb(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("ten_uuid")
    val uuid: String,

    @ColumnInfo("ten_name")
    val name: String?,

    @ColumnInfo("ten_logo")
    val logo: String?,

    @ColumnInfo("ten_site_uuid")
    val siteUuid: String
)