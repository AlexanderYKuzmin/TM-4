package com.kuzmin.tm_4.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.util.TmDatabaseTestUtil
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TmDatabaseTest {

    private lateinit var tmDao: TmDao
    private lateinit var db: TmDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TmDatabase::class.java).build()
        tmDao = db.tmDao()
    }

    @Test
    fun addSiteTest() = runTest {
        val expected = TmDatabaseTestUtil.createTestSite(0)
        tmDao.addSite(expected)

        val actual = tmDao.getSiteByUuid("site0")

        assertEquals(expected, actual)
    }

    @Test
    fun addSiteNoConstruction() = runTest {
        val expected = TmDatabaseTestUtil.createTestBareSite(0)

        tmDao.addSite(expected)

        val actual = tmDao.getAllSiteSimple()

        assertEquals(expected, actual.first())
    }

    @Test
    fun addConstructionAndSections() = runTest {
        val expected = TmDatabaseTestUtil.createConstructionAndSections()

        tmDao.addConstructionAndSections(expected)

        val actual = tmDao.getConstructionAndSections(
            "site0-construction0"
        )

        assertEquals(expected, actual)
    }

    @Test
    fun getAllSiteSample() = runTest {
        TODO()
    }

    @Test
    fun deleteAllTempSites() = runTest {
        val allSites = TmDatabaseTestUtil.createTestSiteDbList()

        val expected = listOf(
            TmDatabaseTestUtil.createTestSite(0)
        )

        tmDao.addSites(allSites)
        tmDao.deleteAllTempSites()

        val actual = tmDao.getAllSiteSimple()

        assertEquals(expected, actual)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}