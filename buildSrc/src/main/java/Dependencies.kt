object Version {
    const val hilt_version = "2.48"
    const val core_version = "1.12.0"
    const val appCompat_version = "1.6.1"
    const val material_version = "1.11.0"
    const val costraintlayout_version = "2.1.4"

    const val nav_version = "2.7.6"

    const val retrofit2_version = "2.9.0"
    const val gson_version = "2.9.0"

    const val lifecycle_version = "2.6.1"

    const val fragment_version = "1.6.2"
    const val activity_version = "1.8.2"

    const val datastore_preferences_version = "1.0.0"
    const val datastore_annotations_version = "1.6.0"
}

object Deps {
    const val core = "androidx.core:core-ktx:${Version.core_version}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat_version}"
    const val material = "com.google.android.material:material:${Version.material_version}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.costraintlayout_version}"

    const val fragment = "androidx.fragment:fragment-ktx:${Version.fragment_version}"
    const val activity = "androidx.activity:activity-ktx:${Version.activity_version}"
}

object LifeCycle {
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle_version}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle_version}"
    const val service = "androidx.lifecycle:lifecycle-service:2.5.1${Version.lifecycle_version}"
}

object DaggerHilt {
    const val hilt = "com.google.dagger:hilt-android:${Version.hilt_version}"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:${Version.hilt_version}"
}

object Navigation {
    const val nav_fragment = "androidx.navigation:navigation-fragment:${Version.nav_version}"
    const val nav_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Version.nav_version}"
    // Feature module Support
    const val nav_features = "androidx.navigation:navigation-dynamic-features-fragment:${Version.nav_version}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit2_version}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit2_version}"
}

object Gson {
    const val gson = "com.google.code.gson:gson:${Version.gson_version}"
}

object DataStore {
    const val datastore_preferences = "androidx.datastore:datastore-preferences:1.0.0"
    const val datastore_annotations = "androidx.annotation:annotation:1.6.0"
}