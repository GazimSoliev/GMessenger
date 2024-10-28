plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(projects.app.domain)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            api(projects.backend.api)
        }
    }
}