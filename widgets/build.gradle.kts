plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Vers.androidCompileSdk

    defaultConfig {
        minSdk = Vers.androidMinSdk
        targetSdk = Vers.androidTargetSdk
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}

dependencies {
    api(project(":common"))
    api(Deps.rxandroid)
    api(Deps.rxbinding)
    api(Deps.splitties)
    api(Deps.recyclerui)
    api(Deps.sandboxui)
}
