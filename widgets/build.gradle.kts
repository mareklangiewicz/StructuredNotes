plugins {
    id("com.android.library")
    kotlin("android")
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
    api(Deps.rxandroid)
    api(Deps.splitties)
    api(Deps.recyclerui)
    api(Deps.sandboxui)
}
