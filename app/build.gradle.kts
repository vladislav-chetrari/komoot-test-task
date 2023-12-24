plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.hilt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

kapt { correctErrorTypes = true }

android {
    namespace = "com.komoot.vchetrari.challenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.komoot.vchetrari.challenge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.schemaLocation" to "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release { isMinifyEnabled = false }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(libs.activity.ktx)
    implementation(libs.android.appCompat)
    implementation(libs.android.core.ktx)
    implementation(libs.android.google.location)
    implementation(libs.android.material)
    implementation(libs.android.worker)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.disabled)
    implementation(libs.glide)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.worker)
    kapt(libs.hilt.worker.compiler)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson)
    implementation(libs.room)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.timber)
    debugImplementation(libs.leakcanary)

    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.turbine)
}