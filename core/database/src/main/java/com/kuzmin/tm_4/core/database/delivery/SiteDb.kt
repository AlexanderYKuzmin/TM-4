package com.kuzmin.tm_4.core.database.delivery

import androidx.room.Embedded
import androidx.room.Relation
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb

data class SiteDb(
    @Embedded
    val siteParamsDb: SiteParamsDb,

    @Embedded
    val tenantDb: TenantDb,

    @Embedded
    val addressDb: AddressDb,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "seq_site_uuid")
    val siteEquipments: List<SiteEquipmentDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "ph_site_uuid")
    val photos: List<PhotoDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "constr_site_uuid")
    val constructions: List<ConstructionDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "s_site_uuid")
    val sections: List<SectionDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "gr_site_uuid")
    val groups: List<GroupDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "mc_site_uuid")
    val measurementsConstructions: List<MeasurementConstructionDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "m_site_uuid")
    val measurements: List<MeasurementDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "r_site_uuid")
    val results: List<ResultDb>,

    @Relation(parentColumn = "sp_site_uuid", entityColumn = "l_site_uuid")
    val levelsInfo: List<LevelDb>
)
