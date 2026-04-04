package com.dimitriskatsikas.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

private const val ANDROID_COMPILE_SDK = "android-compileSdk"
private const val ANDROID_MIN_SDK = "android-minSdk"
private const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.libraryVersion(alias: String): Int {
    return libs.findVersion(alias).get().requiredVersion.toInt()
}

// In AGP 9.0+, ApplicationExtension and LibraryExtension do not share a generic CommonExtension interface
// that exposes `defaultConfig`, `compileOptions`, and `testOptions` cleanly.
// Because the DSL was decoupled to avoid generic hell, this minimal duplication is strictly required
// and is the recommended approach for Gradle convention plugins now.
fun Project.configureAndroid(
    extension: ApplicationExtension,
) {
    extension.apply {
        compileSdk = libraryVersion(ANDROID_COMPILE_SDK)

        defaultConfig {
            minSdk = libraryVersion(ANDROID_MIN_SDK)
            testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
    configureKotlin()
}

fun Project.configureAndroid(
    extension: LibraryExtension,
) {
    extension.apply {
        compileSdk = libraryVersion(ANDROID_COMPILE_SDK)

        defaultConfig {
            minSdk = libraryVersion(ANDROID_MIN_SDK)
            testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
    configureKotlin()
}

private fun Project.configureKotlin() {
    // This block configures all Kotlin compilation tasks to target JVM 17.
    // We do this at the task level to guarantee uniformity across the project.
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}
