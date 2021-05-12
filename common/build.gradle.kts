plugins {
    kotlin("jvm")
}

repositories {
    jcenter()
}

dependencies {
    api(kotlin("stdlib"))
    api(kotlin("reflect"))
    api(Deps.kotlinxCoroutinesCore)
    api(Deps.kotlinxCoroutinesRx3)
    api(Deps.rxjava3)
    api(Deps.rxkotlin)
    api(Deps.rxrelay)
}
