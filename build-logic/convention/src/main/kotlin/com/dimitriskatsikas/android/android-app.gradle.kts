package com.dimitriskatsikas.android

import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    configureAndroid(this)
    defaultConfig {
        targetSdk = libraryVersion("android-targetSdk")
    }
}
