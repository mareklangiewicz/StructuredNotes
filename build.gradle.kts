buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.googleServicesPlugin)
        classpath(Deps.firebaseCrashlyticsPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

