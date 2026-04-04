import com.android.build.api.dsl.LibraryExtension
import com.dimitriskatsikas.android.configureAndroid

plugins {
    id("com.android.library")
}

extensions.configure<LibraryExtension> {
    configureAndroid(this)
}
