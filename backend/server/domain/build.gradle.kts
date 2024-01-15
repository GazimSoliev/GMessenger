plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            // Kotlin LocalDateTime
            implementation(libs.kotlinx.datetime)
        }
    }
}