plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.logback)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.okhttp)

            // Ktor Client
            implementation(libs.ktor.client.core)

            // ContentNegotiation
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Kotlin LocalDateTime
            implementation(libs.kotlinx.datetime)

            // Authentication
            implementation(libs.ktor.client.auth)

            // Logging
            implementation(libs.ktor.client.logging)

            // WebSockets
            implementation(libs.ktor.client.websockets)

            // Resources
            implementation(libs.ktor.client.resources)

            // Models and Route names
            api(projects.backend.common)
        }
    }
}
