plugins {
    alias(libs.plugins.kotlinJvm)
}
group = "com.gazim.gmessenger"
version = "1.0.0"

dependencies {
    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    implementation(projects.backend.server.domain)
    implementation(projects.backend.server.data)
    implementation(projects.backend.server.presentation)
}

kotlin {
    jvmToolchain(8)
}
