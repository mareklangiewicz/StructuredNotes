
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    includeBuild("../DepsKt")
}

plugins {
    id("pl.mareklangiewicz.deps.settings") version "0.2.99"
    // https://plugins.gradle.org/search?term=mareklangiewicz
    id("com.gradle.enterprise") version "3.17.2"
    // https://docs.gradle.com/enterprise/gradle-plugin/
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("GITHUB_ACTIONS") == "true")
        publishOnFailure()
    }
}


include(":common")
include(":logic")
// include(":widgets")
// include(":app")
