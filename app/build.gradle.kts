plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(Vers.androidCompileSdk)
    defaultConfig {
        applicationId = "pl.mareklangiewicz.notes"
        minSdkVersion(Vers.androidMinSdk)
        targetSdkVersion(Vers.androidTargetSdk)
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":logic"))
    implementation(project(":widgets"))
}
