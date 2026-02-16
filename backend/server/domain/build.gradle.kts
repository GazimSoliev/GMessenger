plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    explicitApi()
    compilerOptions {
        allWarningsAsErrors = true
    }

    jvmToolchain(8)
    jvm()

    iosArm64()
    iosSimulatorArm64()

    linuxX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
        }
    }
}
