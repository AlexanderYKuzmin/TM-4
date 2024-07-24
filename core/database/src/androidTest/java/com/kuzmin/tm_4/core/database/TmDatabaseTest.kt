package com.kuzmin.tm_4.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kuzmin.tm_4.core.database.util.TmDatabaseTestUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
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
        val expected = TmDatabaseTestUtil.createTestSite()
        tmDao.addSite(expected)

        val actual = tmDao.getSiteByUuid(TmDatabaseTestUtil.TEST_SITE_UUID)

        assertEquals(expected, actual)
    }

    @Test
    fun addSiteNoConstruction() = runTest {
        val expected = TmDatabaseTestUtil.createTestBareSite()

        tmDao.addSite(expected)

        val actual = tmDao.getAllSiteSimple().first()

        assertEquals(expected, actual)
    }

    @Test
    fun addConstructionAndSections() = runTest {
        val expected = TmDatabaseTestUtil.createConstructionAndSections()

        tmDao.addConstructionAndSections(expected)

        val actual = tmDao.getConstructionAndSections(
            TmDatabaseTestUtil.TEST_SINGLE_CONSTRUCTION_UUID
        )

        assertEquals(expected, actual)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


}