package com.dimitriskatsikas.android

import com.android.build.api.dsl.LibraryExtension

plugins {
    id("com.android.library")
}

extensions.configure<LibraryExtension> {
    configureAndroid(this)
}
