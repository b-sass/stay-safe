import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.madproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.madproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        android.buildFeatures.buildConfig = true
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.androidx.core.ktx) // Core KTX library
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle KTX library
    implementation(libs.androidx.activity.compose) // Activity Compose library
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM for version management
    implementation(libs.androidx.ui) // Compose UI library
    implementation(libs.androidx.ui.graphics) // Compose UI graphics library
    implementation(libs.androidx.ui.tooling.preview) // Compose UI tooling preview
    implementation(libs.androidx.material3) // Material 3 library for Compose

    // Testing dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit for UI testing
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose BOM for testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // Compose UI testing with JUnit4
    debugImplementation(libs.androidx.ui.tooling) // Debug tooling for Compose
    debugImplementation(libs.androidx.ui.test.manifest) // Manifest for UI testing

    // Room Dependencies
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}