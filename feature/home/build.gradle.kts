plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.tasker.android.home"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

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

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    testImplementation(AndroidTest.JUNIT4)
    androidTestImplementation(AndroidTest.EXT_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)

    implementation(AndroidX.LIFECYCLE_VIEWMODEL)

    implementation(AndroidX.NAVIGATION_RUNTIME)
    implementation(AndroidX.NAVIGATION_FRAGMENT)
    implementation(AndroidX.NAVIGATION_UI)

    coreLibraryDesugaring(Android.DESUGAR)

    implementation(Google.HILT)
    kapt(Google.HILT_COMPILER)

    implementation(SquareUp.OKHTTP3_INTERCEPTOR)
    implementation(SquareUp.RETROFIT2)
    implementation(SquareUp.OKHTTP3)
    implementation(SquareUp.GSON_CONVERTER)
}