package com.kuzmin.tm_4.feature.sites.domain

import java.util.Date

data class SiteSimple(
    val id: Long,
    val name: String,
    val address: String,
    val date: Date,
    val employee: String,
    val completed: Boolean,
    val approved: Boolean,
    val firstPhotoId: Long?,
    val firstPhotoUrl: String?
)
