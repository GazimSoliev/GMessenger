plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(projects.app.domain)
            implementation(projects.app.presentation)
            implementation(projects.app.data)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
