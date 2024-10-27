plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.gazim.gmessenger"
version = "1.0.0"

dependencies {
    implementation(projects.backend.server.domain)

    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)

    // Mariadb
    implementation(libs.mariaddb.java.client)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}
