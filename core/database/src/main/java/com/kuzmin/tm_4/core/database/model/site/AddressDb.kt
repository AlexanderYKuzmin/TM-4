package com.kuzmin.tm_4.core.database.model.site

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "addresses",
    indices = [
        Index(
            value = ["addr_site_uuid"]
        )
    ]
)
data class AddressDb(
    @PrimaryKey(false)
    @ColumnInfo("addr_uuid")
    val uuid: String,

    @ColumnInfo("addr_site_uuid")
    val siteUuid: String,

    @ColumnInfo("addr_country")
    val country: String,

    @ColumnInfo("addr_region")
    val region : String,

    @ColumnInfo("addr_reg_code")
    val regionCode: Int?,

    @ColumnInfo("addr_sub_region")
    val subRegion: String,

    @ColumnInfo("addr_city")
    val city: String,

    @ColumnInfo("addr_street")
    val street: String,

    @ColumnInfo("addr_building")
    val building: String,

    @ColumnInfo("addr_post_code")
    val postalCode: String
)
