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
    api(Deps.kotlinxCoroutinesRx2)
    api(Deps.rxjava)
    api(Deps.rxkotlin)
    api(Deps.rxrelay)
}
