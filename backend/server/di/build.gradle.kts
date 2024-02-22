plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.gazim.utils"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    implementation(projects.backend.server.domain)
    implementation(projects.backend.server.data)
    implementation(projects.backend.server.presentation)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
