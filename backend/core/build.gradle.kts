plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()
    jvmToolchain(8)

    iosArm64()
    iosSimulatorArm64()

    linuxX64()

    explicitApi()
    compilerOptions {
        allWarningsAsErrors = true
    }

    sourceSets {
        commonMain.dependencies {
            // Serialization
            implementation(libs.ktor.serialization.kotlinx.json)

            // Resources
            implementation(libs.ktor.resources)
        }
    }
}
