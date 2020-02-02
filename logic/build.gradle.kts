plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":common"))
    testImplementation(Deps.uspek)
    testImplementation(Deps.smokk)
}

tasks.test { useJUnitPlatform() }
