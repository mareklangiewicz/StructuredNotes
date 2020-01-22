plugins {
    kotlin("jvm")
}

dependencies {
    api(Deps.kotlinStdlib8)
    api(Deps.kotlinxCoroutinesCore)
    api(Deps.kotlinxCoroutinesRx2)
    api(Deps.rxjava)
    api(Deps.rxkotlin)
    api(Deps.rxrelay)
    testImplementation(Deps.uspek)
    testImplementation(Deps.smokk)
}

tasks.test { useJUnitPlatform() }
