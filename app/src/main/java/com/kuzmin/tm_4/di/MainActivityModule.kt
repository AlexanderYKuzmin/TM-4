package com.kuzmin.tm_4.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kuzmin.tm_4.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
class MainActivityModule {

    /*@Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_fragment_activity_main)
    }*/
}