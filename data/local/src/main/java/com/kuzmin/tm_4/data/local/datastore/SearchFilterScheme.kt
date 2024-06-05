package com.kuzmin.tm_4.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object SearchFilterScheme {
    val START_DATE = stringPreferencesKey("start_date")
    val END_DATE = stringPreferencesKey("end_date")
    val REGION = stringPreferencesKey("region")
    val CITY = stringPreferencesKey("city")
    val SITE_NAME = stringPreferencesKey("site_name")
}