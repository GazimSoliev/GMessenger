plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {
    implementation(libs.logback)
    implementation(libs.ktor.server.core)

    // Content Negotiation
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // JWT
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)

    // Kotlin DateTime
    implementation(libs.kotlinx.datetime)

    // WebSockets
    implementation(libs.ktor.server.websockets)

    // Resources
    implementation(libs.ktor.server.resources)

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    // Models and Route names
    implementation(projects.backend.common)

    // Domain
    implementation(projects.backend.server.domain)
}
