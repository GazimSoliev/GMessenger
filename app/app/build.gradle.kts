import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    jvm("desktop")
    jvmToolchain(11)

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(libs.compose.runtime)

            implementation(projects.app.presentation)
            implementation(projects.app.domain)
            implementation(projects.app.di)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.asProvider())
            implementation(libs.koin.logger)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.material3)
            implementation(libs.compose.material.icons.extended)
        }
    }
}

android {
    namespace = "com.gazim.gmessenger"
    compileSdk =
        libs.versions.android.compile.sdk
            .get()
            .toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.gazim.gmessenger"
        minSdk =
            libs.versions.android.min.sdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.target.sdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.gazim.gmessenger"
            packageVersion = "1.0.0"
        }
    }
}
