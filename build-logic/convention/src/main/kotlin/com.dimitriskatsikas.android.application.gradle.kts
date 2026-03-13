plugins {
    id("com.android.application")
}

android {
    configureAndroid(this)
    defaultConfig {
        targetSdk = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
    }
}
