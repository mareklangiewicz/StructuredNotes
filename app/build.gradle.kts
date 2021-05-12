plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("android")
}

android {
    compileSdk = Vers.androidCompileSdk
    defaultConfig {
        applicationId = "pl.mareklangiewicz.notes"
        minSdk = Vers.androidMinSdk
        targetSdk = Vers.androidTargetSdk
        versionCode = 4
        versionName = "0.04"
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}

dependencies {
    implementation(project(":logic"))
    implementation(project(":widgets"))
    implementation(Deps.androidxBrowser)
    implementation(platform(Deps.firebaseAndroidBoM))
    implementation(Deps.firebaseAnalyticsKtx)
    implementation(Deps.firebaseCrashlyticsKtx)
}
