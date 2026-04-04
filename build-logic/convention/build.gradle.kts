plugins {
    `kotlin-dsl`
}

group = "com.dimitriskatsikas.android.buildlogic"

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
