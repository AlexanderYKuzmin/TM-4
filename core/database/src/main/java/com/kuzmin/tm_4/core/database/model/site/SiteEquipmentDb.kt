package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "site_equipments",
    indices = [
        Index(
            value = ["seq_site_uuid"]
        )
    ]
)
data class SiteEquipmentDb(
    @PrimaryKey(false)
    @ColumnInfo("seq_uuid")
    val uuid: String,

    @ColumnInfo("seq_site_uuid")
    val siteUuid: String,

    @ColumnInfo("seq_type")
    val type: String,

    @ColumnInfo("seq_name")
    val name: String,
)
