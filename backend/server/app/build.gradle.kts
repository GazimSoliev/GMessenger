plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
}

group = "com.gazim.gmessenger"
version = "1.0.0"
application {
    mainClass.set("com.gazim.gmessenger.server.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)

    // DI
    implementation(projects.backend.server.di)

    // Presentation
    implementation(projects.backend.server.presentation)
}
