package com.kuzmin.tm_4.feature.api.model.site

data class Construction(

    val uuid: String,

    val version: Int,

    val description: String,

    val status: String,

    val numOfSections: Int,

    val height: Int,

    val constructionType: String,

    val config: String,

    val measureLevels: Int?,

    val siteUuid: String
)
