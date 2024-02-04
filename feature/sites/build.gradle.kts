plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.kuzmin.tm_4.feature.sites"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }


}

dependencies {
    implementation(project(":common"))

    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)
    implementation(Deps.activity)
    implementation(Deps.fragment)

    implementation(LifeCycle.liveData)
    implementation(LifeCycle.viewModel)
    implementation(LifeCycle.service)

    implementation(Navigation.nav_fragment)
    implementation(Navigation.nav_ui_ktx)

    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hilt_compiler)

    implementation(Picasso.picasso)


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}