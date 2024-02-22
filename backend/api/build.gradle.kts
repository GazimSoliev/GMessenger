import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildkonfig)
}

buildkonfig {
    packageName = "com.gmessenger.backend.config"

    defaultConfigs {
        val hostPrefixProp = "hostPrefix"
        val hostProp = "host"
        val localProperties = gradleLocalProperties(rootDir)
        val hostPrefix = localProperties.getProperty(hostPrefixProp)
        val host = localProperties.getProperty(hostProp)
        buildConfigField(STRING, hostPrefixProp, hostPrefix)
        buildConfigField(STRING, hostProp, host)
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.logback)
        }
        commonMain.dependencies {
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

            // Models and Route names
            api(projects.backend.common)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
