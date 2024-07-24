package com.kuzmin.tm_4.core.database.model.delivery

import com.kuzmin.tm_4.core.database.model.site.ConstructionDb

data class ConstructionAndMcFullSingle(
    val constructionDb: ConstructionDb,

    val mcDbFull: McDbFull
)