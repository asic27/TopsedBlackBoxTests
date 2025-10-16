plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.allure)
}

android {
    namespace = "com.example.topsedblackboxtests"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.topsedblackboxtests"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "io.qameta.allure.android.runners.AllureAndroidJUnitRunner"
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
    }
    allure {
        version.set("2.24.0")
        adapter {
            aspectjWeaver = true
            frameworks {
                junit4 {
                    adapterVersion.set("2.24.0")
                }
            }
        }
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
    implementation(libs.androidx.uiautomator)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
    androidTestImplementation ("io.qameta.allure:allure-kotlin-model:2.4.0")
    androidTestImplementation ("io.qameta.allure:allure-kotlin-commons:2.4.0")
    androidTestImplementation ("io.qameta.allure:allure-kotlin-junit4:2.4.0")
    androidTestImplementation ("io.qameta.allure:allure-kotlin-android:2.4.0")

    androidTestUtil("androidx.test:orchestrator:1.5.1")
}