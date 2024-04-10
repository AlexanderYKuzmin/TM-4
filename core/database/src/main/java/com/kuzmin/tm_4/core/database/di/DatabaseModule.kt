package com.kuzmin.tm_4.core.database.di

import android.content.Context
import com.kuzmin.tm_4.core.database.TmDao
import com.kuzmin.tm_4.core.database.TmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TmDatabase {
        return TmDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideDao(tmDatabase: TmDatabase): TmDao {
        return tmDatabase.tmDao()
    }
}