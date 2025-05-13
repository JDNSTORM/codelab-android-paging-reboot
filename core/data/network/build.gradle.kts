plugins {
    alias(libs.plugins.projectKmp)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.core.data.network"
        minSdk = 24
        compileSdk = 35
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
        commonMain.dependencies {
            implementation(projects.core.models)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.ktor.client.android)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}