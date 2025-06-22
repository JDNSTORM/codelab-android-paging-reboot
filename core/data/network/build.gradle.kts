import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.iosTarget

plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.core.data.network"
        minSdk = 24
        compileSdk = 35
    }
    iosTarget("coreDataNetworkKit")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        commonMain.dependencies {
            implementation(projects.core.models)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}