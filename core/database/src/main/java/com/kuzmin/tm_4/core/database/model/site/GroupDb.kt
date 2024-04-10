package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "groups",
    indices = [
        Index(
            value = ["gr_site_uuid", "gr_meas_constr_uuid"]
        )
    ]
)
data class GroupDb(
    @PrimaryKey(false)
    @ColumnInfo("gr_uuid")
    val uuid: String,

    @ColumnInfo("gr_site_uuid")
    val siteUuid: String,

    @ColumnInfo("gr_meas_constr_uuid")
    val measurementConstructionUuid: String,

    @ColumnInfo("gr_num")
    val groupNum: Int,

    @ColumnInfo("gr_azimuth")
    val azimuth: Int,

    @ColumnInfo("gr_theo_dist")
    val theoDistance: Int,

    @ColumnInfo("gr_theo_height")
    val theoHeight: Int,
)
