package com.dimitriskatsikas.android

pluginManager.apply("com.google.devtools.ksp")
pluginManager.apply("com.google.dagger.hilt.android")

dependencies {
    "implementation"(libs.findLibrary("hilt.android").get())
    "ksp"(libs.findLibrary("hilt.compiler").get())
}
