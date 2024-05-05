package com.kuzmin.tm_4.core.database.delivery

import androidx.room.Embedded
import androidx.room.Relation
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb

data class ConstructionFullDb(

    /*@Embedded
    val constructionDb: ConstructionDb,

    @Relation(parentColumn = "constr_uuid", entityColumn = "s_constr_uuid")
    val sections: List<SectionDb>,

    @Relation(parentColumn = "constr_uuid", entityColumn = "mc_constr_uuid")
    val measurementConstructions: List<MeasurementConstructionDb>,

    @Relation(parentColumn = "constr_uuid", entityColumn = "gr_meas_constr_uuid")
    val groups: List<GroupDb>,

    @Relation(parentColumn = "mc_uuid", entityColumn = "m_mc_uuid")
    val measurements: List<MeasurementDb>,

    @Relation(parentColumn = "mc_uuid", entityColumn = "r_mc_uuid")
    val results: List<ResultDb>*/

    val constructionAndSectionsDb: ConstructionAndSectionsDb,

    val mcFullDbList: List<McDbFull>
)