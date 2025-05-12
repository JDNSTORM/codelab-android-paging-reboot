plugins {
    alias(libs.plugins.projectJvmLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
}
dependencies {
    implementation(projects.core.models)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.bundles.ktor)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.serialization.json)
}