buildscript {
    repositories {
        google()
        jcenter()
        
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
        maven("https://jitpack.io")
    }
}

