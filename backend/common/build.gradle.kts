plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()
    jvmToolchain(8)

    sourceSets {
        commonMain.dependencies {
            // Serialization
            implementation(libs.ktor.serialization.kotlinx.json)

            // Kotlin LocalDateTime
            implementation(libs.kotlinx.datetime)

            // Resources
            implementation(libs.ktor.resources)

            implementation(projects.serialization)
        }
    }
}