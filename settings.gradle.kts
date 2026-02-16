@file:Suppress("UnstableApiUsage")

rootProject.name = "GMessenger"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":app:app",
    ":app:domain",
    ":app:data",
    ":backend:api",
    ":app:di",
    ":app:presentation",
    ":backend:core",
    ":backend:server:domain",
    ":backend:server:data",
    ":backend:server:presentation",
    ":backend:server:di",
    ":backend:server:app",
)
