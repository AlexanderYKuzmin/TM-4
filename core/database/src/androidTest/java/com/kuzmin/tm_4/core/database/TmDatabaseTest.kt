package com.kuzmin.tm_4.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
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

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


}