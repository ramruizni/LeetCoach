import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // TODO: Check if alias can be used here
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val secretPropertiesFile = rootProject.file("secret.properties")
val secretProperties = Properties()
secretProperties.load(FileInputStream(secretPropertiesFile))

android {
    namespace = "com.puadevs.leetcoach"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.puadevs.leetcoach"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY_OPEN_AI", "\"${secretProperties["API_KEY_OPEN_AI"]}\"")
        buildConfigField("String", "API_KEY_OPEN_ROUTER", "\"${secretProperties["API_KEY_OPEN_ROUTER"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // MLKit dependencies
    implementation(libs.androidx.activity.compose.v190)
    implementation(libs.text.recognition)

    // CanHub Image Cropper
    implementation(libs.android.image.cropper)
    implementation(libs.android.image.cropper.v450)

    //koroutines
    implementation(libs.kotlinx.coroutines.play.services)

    // interceptor
    implementation(libs.logging.interceptor)

    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //coil
    implementation(libs.coil.compose)
}