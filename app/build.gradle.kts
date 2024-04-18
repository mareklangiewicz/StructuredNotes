@file:Suppress("DEPRECATION", "unused", "UnusedVariable")

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*

plugins {
    plugAll(plugs.AndroAppNoVer, plugs.KotlinAndro, plugs.MavenPublish, plugs.Signing)
}

defaultBuildTemplateForAndroApp()

// android {
//     compileSdk = Vers.androidCompileSdk
//     defaultConfig {
//         applicationId = "pl.mareklangiewicz.notes"
//         minSdk = Vers.androidMinSdk
//         targetSdk = Vers.androidTargetSdk
//         versionCode = 4
//         versionName = "0.04"
//     }
//     kotlinOptions {
//         jvmTarget = "1.8"
//         useIR = true
//     }
// }

dependencies {
    implementation(project(":logic"))
    implementation(project(":widgets"))
    // implementation(Deps.androidxBrowser)
    // implementation(platform(Deps.firebaseAndroidBoM))
    // implementation(Deps.firebaseAnalyticsKtx)
    // implementation(Deps.firebaseCrashlyticsKtx)
}
