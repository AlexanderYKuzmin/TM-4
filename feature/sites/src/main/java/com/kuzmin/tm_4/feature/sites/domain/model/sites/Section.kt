package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class Section(

    val uuid: String,

    val number: Int,

    val wBottom: Int,

    val wTop: Int,

    val height: Int,

    val level: Int? = null,         // probably delete

    val status: String,

    val constructionUuid: String
)
