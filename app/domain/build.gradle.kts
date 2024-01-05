plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "com.gazim.gmessenger"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.test)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}