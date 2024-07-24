
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
//    alias(libs.plugins.buildkonfig)
}

// buildkonfig {
//    packageName = "com.gmessenger.backend.config"
//
//    defaultConfigs {
//        val hostPrefixProp = "hostPrefix"
//        val wsPrefixProp = "wsPrefix"
//        val hostProp = "host"
//        val localProperties = gradleLocalProperties(rootDir)
//        val hostPrefix = localProperties.getProperty(hostPrefixProp)
//        val wsPrefix = localProperties.getProperty(wsPrefixProp)
//        val host = localProperties.getProperty(hostProp)
//        buildConfigField(STRING, hostPrefixProp, hostPrefix)
//        buildConfigField(STRING, wsPrefixProp, wsPrefix)
//        buildConfigField(STRING, hostProp, host)
//    }
// }

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.logback)
        }
        commonMain.dependencies {
            implementation(libs.ktor.client.okhttp)

            // Ktor Client
            implementation(libs.ktor.client.core)

            // ContentNegotiation
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Kotlin LocalDateTime
            implementation(libs.kotlinx.datetime)

            // Authentication
            implementation(libs.ktor.client.auth)

            // Logging
            implementation(libs.ktor.client.logging)

            // WebSockets
            implementation(libs.ktor.client.websockets)

            // Resources
            implementation(libs.ktor.client.resources)

            // Models and Route names
            api(projects.backend.common)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger.api"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()
    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }
}
