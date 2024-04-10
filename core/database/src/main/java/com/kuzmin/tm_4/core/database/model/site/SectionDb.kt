package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sections",
    indices = [
        Index(
            value = ["s_site_uuid", "s_constr_uuid"]
        )
    ]
)
data class SectionDb(
    @PrimaryKey(false)
    @ColumnInfo("s_uuid")
    val uuid: String,

    @ColumnInfo("s_site_uuid")
    val siteUuid: String,

    @ColumnInfo("s_constr_uuid")
    val constructionUuid: String,

    @ColumnInfo("s_number")
    val number: Int,

    @ColumnInfo("s_w_bottom")
    val wBottom: Int,

    @ColumnInfo("s_w_top")
    val wTop: Int,

    @ColumnInfo("s_height")
    val height: Int,

    @ColumnInfo("s_level")
    val level: Int?,         // probably delete

    @ColumnInfo("s_status")
    val status: String,
)
