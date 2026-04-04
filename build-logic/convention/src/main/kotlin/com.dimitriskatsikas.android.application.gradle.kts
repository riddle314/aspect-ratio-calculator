import com.android.build.api.dsl.ApplicationExtension
import com.dimitriskatsikas.android.configureAndroid
import com.dimitriskatsikas.android.libraryVersion

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    configureAndroid(this)
    defaultConfig {
        targetSdk = libraryVersion("android-targetSdk")
    }
}
