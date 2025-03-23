plugins {
    alias(libs.plugins.kotlin.jvm)
}
group = "com.gazim.gmessenger"
version = "1.0.0"
dependencies {
    implementation(libs.kotlinx.coroutines.core)
}

kotlin {
    jvmToolchain(8)
}
