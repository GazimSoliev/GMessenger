plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.gazim.gmessenger.api"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.test)

    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)

    // ContentNegotiation
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Kotlin LocalDateTime
    implementation(libs.kotlinx.datetime)

    // Authentication
    implementation(libs.ktor.client.auth)

    // Logging
    implementation(libs.logback)
    implementation(libs.ktor.client.logging)

    // WebSockets
    implementation(libs.ktor.client.websockets)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}