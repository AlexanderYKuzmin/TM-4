package com.kuzmin.tm_4.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb

@Database(
    entities = [
        SiteParamsDb::class,
        TenantDb::class,
        AddressDb::class,
        PhotoDb::class,
        SiteEquipmentDb::class,
        ConstructionDb::class,
        SectionDb::class,
        MeasurementConstructionDb::class,
        GroupDb::class,
        MeasurementDb::class,
        ResultDb::class
    ],
    version = 2
)
abstract class TmDatabase : RoomDatabase() {
    companion object {

        private var db: TmDatabase? = null
        private const val DB_NAME = "local_tm.db"
        private val LOCK = Any()

        fun getInstance(context: Context): TmDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        TmDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun tmDao(): TmDao
}