plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.kuzmin.tm_4.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        /*javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
            }
        }*/
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
}

dependencies {

    implementation(project(":common"))

    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.material)

    implementation(DataBase.room)
    implementation(DataBase.room_common)
    kapt(DataBase.room_kapt_compiler)
    implementation(DataBase.room_paging)
    implementation(DataBase.room_ktx)

    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hilt_compiler)

    testImplementation(Test.junit4)
    testImplementation (Test.coroutine_test)
    androidTestImplementation(AndroidTest.extJunit4)
    androidTestImplementation(AndroidTest.espresso)
    androidTestImplementation(AndroidTest.coroutine_test)
}