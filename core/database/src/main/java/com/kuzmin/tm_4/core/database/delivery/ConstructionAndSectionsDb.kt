package com.kuzmin.tm_4.core.database.delivery

import androidx.room.Embedded
import androidx.room.Relation
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb

data class ConstructionAndSectionsDb(
    @Embedded
    val constructionDb: ConstructionDb,

    @Relation(
        entity = SectionDb::class,
        parentColumn = "constr_uuid",
        entityColumn = "s_constr_uuid"
    )
    val sections: List<SectionDb>
)
