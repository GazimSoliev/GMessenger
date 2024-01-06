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
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":app:composeApp")
include(":app:domain")
include(":app:data")
include(":backend:server")
include(":backend:api")
include("app:di")
findProject(":app:di")?.name = "di"
include("app:presentation")
findProject(":app:presentation")?.name = "presentation"
