buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.kotlinGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

