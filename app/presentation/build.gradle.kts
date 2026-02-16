import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.preview)

            implementation(projects.app.domain)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose.asProvider())
            implementation(libs.koin.compose.viewmodel)

            // DateTime
            implementation(libs.kotlinx.datetime)

            // Orbit
            implementation(libs.orbit.core)
//            implementation(libs.orbit.compose)
//            implementation(libs.orbit.viewmodel)

            implementation(libs.paging.common)
            implementation(libs.paging.compose.common)

            // ViewModel
            implementation(libs.lifecycle.viewmodel)

            // Navigation
            implementation(libs.compose.navigation)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.filekit.compose)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger.presentation"
    compileSdk =
        libs.versions.android.compile.sdk
            .get()
            .toInt()
    defaultConfig {
        minSdk =
            libs.versions.android.min.sdk
                .get()
                .toInt()
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
}
