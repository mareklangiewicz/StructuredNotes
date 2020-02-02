plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Vers.androidCompileSdk)
    defaultConfig {
        minSdkVersion(Vers.androidMinSdk)
        targetSdkVersion(Vers.androidTargetSdk)
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    api(project(":common"))
    api(Deps.splitties)
    api(Deps.rxandroid)
}
