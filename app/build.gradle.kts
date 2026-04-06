import java.util.Properties

plugins {
    id("com.dimitriskatsikas.plugins.android-app")
    id("com.dimitriskatsikas.plugins.android-compose")
    alias(libs.plugins.kotlin.serialization)
    id("com.dimitriskatsikas.plugins.android-hilt")
}

android {
    namespace = "com.dimitriskatsikas.ratiocalculator"

    defaultConfig {
        applicationId = "com.dimitriskatsikas.ratiocalculator"
        versionCode = 2
        versionName = "1.1"
    }

    buildTypes {
        debug {
            // Add ids for test ads on debug
            manifestPlaceholders["ADMOB_APP_ID"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField(
                type = "String",
                name = "BANNER_AD_UNIT_ID",
                value = "\"ca-app-pub-3940256099942544/6300978111\""
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Load local.properties
            val properties = Properties()
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use { properties.load(it) }
            }
            val admobAppId = properties.getProperty("ADMOB_APP_ID") ?: "MISSING_ADMOB_ID"
            val bannerAdUnitId = properties.getProperty("BANNER_AD_UNIT_ID") ?: "MISSING_BANNER_ID"

            // Add ids for release
            manifestPlaceholders["ADMOB_APP_ID"] = admobAppId
            buildConfigField(
                type = "String",
                name = "BANNER_AD_UNIT_ID",
                value = "\"$bannerAdUnitId\""
            )
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.play.services.ads)
    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
    implementation(project(":feature:calculator"))
    implementation(project(":feature:info"))
}
