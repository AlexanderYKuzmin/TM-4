package com.kuzmin.tm_4.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object SiteScheme {
    val SITE_UUID = stringPreferencesKey("site")
    val CONSTRUCTION_UUID = stringPreferencesKey("construction")
    val MEASUREMENT_CONSTRUCTION_UUID = stringPreferencesKey("m_construction")
}