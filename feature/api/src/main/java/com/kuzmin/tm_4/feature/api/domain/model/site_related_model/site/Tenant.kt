package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site

import java.util.UUID

data class Tenant(

    val uuid: String = UUID.randomUUID().toString(),

    val name: String,

    val logo: String?
)
