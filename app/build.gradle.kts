import java.io.FileInputStream
import java.util.Properties
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
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

        buildConfigField("String", "MAP_API_GOOGLE", "\"${getApiGet()}\"")
        manifestPlaceholders["API_KEY"] = getApiGet()
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Ensure this matches your Compose version
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
    }
}

fun getApiGet(): String
{
    val properties = Properties()
    properties.load(FileInputStream("local.properties"))
    return properties.getProperty("MAP_API_GOOGLE")
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

    // Room dependencies
    implementation(libs.androidx.room.runtime) // Room runtime library
    implementation(libs.androidx.room.ktx) // Room KTX library for coroutines
    //ksp(libs.androidx.room.compiler) // KSP for Room

    // Testing dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit for UI testing
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose BOM for testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // Compose UI testing with JUnit4
    debugImplementation(libs.androidx.ui.tooling) // Debug tooling for Compose
    debugImplementation(libs.androidx.ui.test.manifest) // Manifest for UI testing
}