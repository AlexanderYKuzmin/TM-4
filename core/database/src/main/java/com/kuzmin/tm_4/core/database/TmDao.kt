package com.kuzmin.tm_4.core.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndMcFullSingle
import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.core.database.model.delivery.ConstructionFullDb
import com.kuzmin.tm_4.core.database.model.delivery.GroupFullDb
import com.kuzmin.tm_4.core.database.model.delivery.McDbFull
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb

@Dao
interface TmDao {

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSite(
        siteParamsDb: SiteParamsDb,
        tenantDb: TenantDb,
        addressDb: AddressDb,
        siteEquipment: SiteEquipmentDb,
        photos: List<PhotoDb>,
        constructions: List<ConstructionDb>,
        measurementsConstructions: List<MeasurementConstructionDb>,
        groups: List<GroupDb>,
        measurements: List<MeasurementDb>,
        results: List<ResultDb>
    ): Long*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSiteParams(siteParamsDb: SiteParamsDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTenant(tenantDb: TenantDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(addressDb: AddressDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSiteEquipment(siteEquipmentDb: SiteEquipmentDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotos(photos: List<PhotoDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addConstructions(constructions: List<ConstructionDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSections(sections: List<SectionDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGroups(groups: List<GroupDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeasurementsConstructions(measurementsConstructions: List<MeasurementConstructionDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMeasurements(measurements: List<MeasurementDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addResults(results: List<ResultDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLevelsInfo(levels: List<LevelDb>)

    /*@Transaction
    @Insert
    abstract suspend fun addMeasurementsConstructionsComplex(
        measurementsConstructions: List<MeasurementConstructionDb>,
        groups: List<GroupDb>,
        measurements: List<MeasurementDb>,
        results: List<ResultDb>
    )*/

    @Transaction
    suspend fun addSite(siteDb: SiteDb) {
        with(siteDb) {
            addSiteParams(siteParamsDb)
            addTenant(tenantDb)
            addAddress(addressDb)

            if (siteEquipments.isNotEmpty()) addSiteEquipment(siteEquipments.first())

            addPhotos(photos)
            addConstructions(constructions)
            addSections(sections)
            addMeasurementsConstructions(measurementsConstructions)
            addGroups(groups)
            addMeasurements(measurements)
            addResults(results)
            addLevelsInfo(levelsInfo)
        }
    }

    @Transaction
    suspend fun addConstructionAndSections(constructionAndSectionsDb: ConstructionAndSectionsDb) {
        addConstructions(listOf(constructionAndSectionsDb.constructionDb))
        addSections(constructionAndSectionsDb.sections)
    }

    @Query(
        "SELECT  *, *, *, * FROM site_params " +
        "JOIN addresses ON sp_site_uuid = addr_site_uuid " +
        "JOIN tenants ON sp_site_uuid = ten_site_uuid " //+
        //"JOIN measurements_constructions ON sp_site_uuid = mc_site_uuid"
    )
    fun getAllSiteSimple(): List<SiteDb>

    @Transaction
    @Query(
        "SELECT  *, *, * FROM site_params " +
        "JOIN addresses ON sp_site_uuid = addr_site_uuid " +
        "JOIN tenants ON sp_site_uuid = ten_site_uuid " +
        "JOIN constructions ON sp_site_uuid = constr_site_uuid " +
        "JOIN sections ON sp_site_uuid = s_site_uuid " +
        "WHERE sp_site_uuid = :uuid"
    )
    fun getSiteByUuid(uuid: String): SiteDb

    @Transaction
    @Query("SELECT  *, *, * FROM site_params " +
            "JOIN addresses ON sp_site_uuid = addr_site_uuid " +
            "JOIN tenants ON sp_site_uuid = ten_site_uuid")
    suspend fun getSite(): SiteDb

    @Query("SELECT * FROM constructions WHERE constr_uuid == :cUuid")
    suspend fun getConstructionAndSections(cUuid: String): ConstructionAndSectionsDb

    @Transaction
    suspend fun getConstructionFull(cUuid: String): ConstructionFullDb {
        return ConstructionFullDb(
            getConstructionAndSections(cUuid),
            getMcFullListByConstructionUuid(cUuid)
        )
    }

    @Transaction
    suspend fun getMcAndConstructionByMcUuid(mcUuid: String): ConstructionAndMcFullSingle {
        val mc = getMcFull(mcUuid)
        Log.d("report"," MC in tm dao: MC = $mc")
        val construction = getConstruction(mc.mcDb.constructionUuid)
        return ConstructionAndMcFullSingle(construction, mc)
    }

    @Query("SELECT * FROM constructions WHERE constr_uuid == :cUuid")
    suspend fun getConstruction(cUuid: String): ConstructionDb

    /*@Query("SELECT * FROM constructions WHERE constr_uuid = :cUuid")
    suspend fun getConstructionFull(cUuid: String): ConstructionFullDb*/

    @Query("SELECT * FROM measurements_constructions WHERE mc_constr_uuid = :cUuid")
    suspend fun getMcFullListByConstructionUuid(cUuid: String): List<McDbFull>

    @Transaction
    @Query("SELECT * FROM measurements_constructions WHERE mc_uuid = :mcUuid")
    suspend fun getMcFull(mcUuid: String): McDbFull

    /*@Transaction
    @Query("SELECT * FROM measurements_constructions WHERE mc_constr_uuid = :cUuid")
    suspend fun getAllMcFullByConstruction(cUuid: String): List<McDbFull>*/

    @Query("SELECT * FROM measurements_constructions WHERE mc_uuid = :mcUuid" )
    suspend fun getMc(mcUuid: String): MeasurementConstructionDb

    @Query("SELECT * FROM groups WHERE gr_meas_constr_uuid = :mcUuid AND gr_num = :groupNum")
    suspend fun getGroupFull(groupNum: Int, mcUuid: String): GroupFullDb
}