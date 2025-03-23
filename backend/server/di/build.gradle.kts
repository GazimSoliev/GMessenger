plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Koin
    implementation(libs.ktor.server.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    implementation(projects.backend.server.domain)
    implementation(projects.backend.server.data)
    implementation(projects.backend.server.presentation)
}

kotlin {
    jvmToolchain(8)
}
