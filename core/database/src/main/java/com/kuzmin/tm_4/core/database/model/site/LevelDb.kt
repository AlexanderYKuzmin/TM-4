package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("levels")
data class LevelDb(
    @PrimaryKey(true)
    val id: Long,

    val number: Int,

    @ColumnInfo("position_of_sec")
    val position: String,

    val altitude: Int
)
