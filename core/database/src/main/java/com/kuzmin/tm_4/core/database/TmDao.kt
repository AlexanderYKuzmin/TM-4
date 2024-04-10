package com.kuzmin.tm_4.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SiteDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb

@Dao
abstract class TmDao {

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

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSiteParams(siteParamsDb: SiteParamsDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addTenant(tenantDb: TenantDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addAddress(addressDb: AddressDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addSiteEquipment(siteEquipmentDb: SiteEquipmentDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addPhotos(photos: List<PhotoDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addConstructions(constructions: List<ConstructionDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addGroups(groups: List<GroupDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addMeasurementsConstructions(measurementsConstructions: List<MeasurementConstructionDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addMeasurements(measurements: List<MeasurementDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addResults(results: List<ResultDb>)

    @Transaction
    @Insert
    abstract suspend fun addMeasurementsConstructionsComplex(
        measurementsConstructions: List<MeasurementConstructionDb>,
        groups: List<GroupDb>,
        measurements: List<MeasurementDb>,
        results: List<ResultDb>
    )

    @Transaction
    suspend fun addSite(siteDb: SiteDb) {
        with(siteDb) {
            addSiteParams(siteParamsDb)
            addTenant(tenantDb)
            addAddress(addressDb)
            addSiteEquipment(siteEquipments.first())
            addPhotos(photos)
            addConstructions(constructions)
            addMeasurementsConstructions(measurementsConstructions)
            addGroups(groups)
            addMeasurements(measurements)
            addResults(results)
        }
    }*/

    /*@Query("SELECT * FROM site_params")
    abstract suspend fun getAll(): SiteDb*/



}