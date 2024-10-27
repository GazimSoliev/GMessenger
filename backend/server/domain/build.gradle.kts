plugins {
    alias(libs.plugins.kotlinJvm)
}
group = "com.gazim.gmessenger"
version = "1.0.0"
dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

kotlin {
    jvmToolchain(8)
}