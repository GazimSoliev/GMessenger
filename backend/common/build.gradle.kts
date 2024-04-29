import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            // Serialization
            implementation(libs.ktor.serialization.kotlinx.json)

            // Kotlin LocalDateTime
            implementation(libs.kotlinx.datetime)

            // Resources
            implementation(libs.ktor.resources)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger.backend.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
