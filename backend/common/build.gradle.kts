plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
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
        }
    }
}
