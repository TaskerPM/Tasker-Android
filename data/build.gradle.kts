plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tasker.android.data"
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
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(Google.HILT)
    kapt(Google.HILT_COMPILER)

    implementation(SquareUp.OKHTTP3)
    implementation(SquareUp.OKHTTP3_INTERCEPTOR)
    implementation(SquareUp.RETROFIT2)
    implementation(SquareUp.GSON_CONVERTER)

    implementation(AndroidX.ROOM_RUNTIME)
    kapt(AndroidX.ROOM_COMPILER)
    implementation(AndroidX.ROOM_KTX)

}