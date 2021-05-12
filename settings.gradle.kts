@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("../../kotlin/deps.kt")
}

plugins {
    id("pl.mareklangiewicz.deps.settings")
}

include(":common")
include(":logic")
include(":widgets")
include(":app")
