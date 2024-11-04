@file:Suppress("UnstableApiUsage")

rootProject.name = "GMessenger"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
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
    ":utils",
    ":backend:common",
    ":backend:server:domain",
    ":backend:server:data",
    ":backend:server:presentation",
    ":backend:server:di",
    ":backend:server:app",
    ":serialization",
)
