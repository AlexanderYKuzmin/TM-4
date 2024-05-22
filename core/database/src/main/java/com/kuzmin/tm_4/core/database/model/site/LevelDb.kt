package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "levels",
    indices = [
        Index(
            value = ["level_num", "l_mc_uuid"], unique = true
        )
    ]
)
data class LevelDb(
    @PrimaryKey(autoGenerate = false)
    val uuid: String,

    @ColumnInfo("level_num")
    val levelNum: Int,

    /*@ColumnInfo("position_of_sec")
    val position: String,*/

    val shift: Int,

    @ColumnInfo("is_serviceable")
    val isServiceable: Boolean,

    val altitude: Int,

    @ColumnInfo("l_mc_uuid")
    val mcUuid: String,

    @ColumnInfo("l_site_uuid")
    val sUuid: String
)
