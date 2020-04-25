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
        versionCode = 2
        versionName = "0.02"
    }
}

dependencies {
    implementation(project(":logic"))
    implementation(project(":widgets"))
    implementation(Deps.androidxBrowser)
}
