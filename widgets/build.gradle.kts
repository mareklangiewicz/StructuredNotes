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
    implementation(Deps.kotlinStdlib8)
    api(Deps.splitties)
}
