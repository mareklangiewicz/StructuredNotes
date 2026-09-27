@file:Suppress("UnusedVariable", "unused")


// region [[Full MPP Lib Build Imports and Plugs]]

import com.android.build.api.dsl.*
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.compose.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import pl.mareklangiewicz.defaults.*
import pl.mareklangiewicz.deps.*
import pl.mareklangiewicz.utils.*
import pl.mareklangiewicz.templatefun.*

plugins {
  plugAll(
    plugs.TemplateFunNoVer, // version comes from the root: a versioned request here fails in composite builds
    plugs.KotlinMulti,
    plugs.KotlinMultiCompose,
    plugs.ComposeJbNoVer,
    plugs.VannikPublish,
  )
  plug(plugs.AndroKmpNoVer) apply false // applied conditionally by defaultBuildTemplateForFullMppLib
}

// endregion [[Full MPP Lib Build Imports and Plugs]]


// workaround for crazy gradle bugs like this one or similar:
// https://youtrack.jetbrains.com/issue/KT-43500/KJS-IR-Failed-to-resolve-Kotlin-library-on-attempting-to-resolve-compileOnly-transitive-dependency-from-direct-dependency
// repositories { maven(repos.composeJbDev) }


defaultBuildTemplateForFullMppLib {
// workaround for crazy gradle bugs like this one or similar:
// https://youtrack.jetbrains.com/issue/KT-43500/KJS-IR-Failed-to-resolve-Kotlin-library-on-attempting-to-resolve-compileOnly-transitive-dependency-from-direct-dependency
  implementation(KotlinX.coroutines_core)
}

kotlin {
  sourceSets {
    jvmMain {

      dependencies {
        api(kotlin("stdlib"))
        api(kotlin("reflect"))
        api(KotlinX.coroutines_core)
        api(KotlinX.coroutines_rx3)
        api(Io.ReactiveX.RxJava3.rxkotlin)
        api(Com.JakeWharton.RxRelay3.rxrelay)
      }
    }
  }

}
