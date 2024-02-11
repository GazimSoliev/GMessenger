plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.gazim.gmessenger"
version = "1.0.0"
application {
    mainClass.set("com.gazim.gmessenger.server.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(projects.app.data)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)

    // Mariadb
    implementation(libs.mariaddb.java.client)

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

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    // Models and Route names
    implementation(projects.backend.common)

    // Domain
    implementation(projects.backend.server.domain)

    // DI
    implementation(projects.backend.server.di)
}
