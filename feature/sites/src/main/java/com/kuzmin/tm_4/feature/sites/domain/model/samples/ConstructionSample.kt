package com.kuzmin.tm_4.feature.sites.domain.model.samples

import java.util.Date

data class ConstructionSample (
    val constructionType: String,

    val config: String,

    val heightMm: Int,

    val creationDate: Date? = null,        //yyyy-MM-dd

    val completedDate: Date? = null       //yyyy-MM-dd
)