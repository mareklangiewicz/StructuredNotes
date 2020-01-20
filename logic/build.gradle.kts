plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Deps.kotlinStdlib8)
    testImplementation(Deps.uspek)
    testImplementation(Deps.smokk)
}

tasks.test { useJUnitPlatform() }
