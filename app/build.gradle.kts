plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
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
    implementation(Deps.kotlinStdlib8)
    implementation(project(":logic"))
    implementation(project(":widgets"))
}
