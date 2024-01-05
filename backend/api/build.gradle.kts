val ktorVersion = "2.3.7"

plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.gazim.gmessenger.api"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Ktor Client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    // ContentNegotiation
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Kotlin LocalDateTime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    // Authentication
    implementation("io.ktor:ktor-client-auth:$ktorVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    // WebSockets
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}