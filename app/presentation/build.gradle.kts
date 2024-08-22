import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(compose.material3)
            implementation(compose.materialIconsExtended)

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

            // Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.koin)

            // Utils
            implementation(projects.utils)

            implementation(libs.paging.common)
            implementation(libs.paging.compose.common)

            // ViewModel
            implementation(libs.lifecycle.viewmodel)

            // Navigation
            implementation(libs.navigation.compose)

            implementation(libs.kotlinx.serialization.json)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger.presentation"
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
