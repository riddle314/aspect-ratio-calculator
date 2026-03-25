plugins {
    id("com.android.application")
}

android {
    configureAndroid(this)
    defaultConfig {
        targetSdk = getVersion("android-targetSdk")
    }
}
