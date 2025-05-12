plugins {
    alias(libs.plugins.projectJvmLibrary)
}
dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.mock)
    implementation(libs.logging.interceptor)
    implementation(projects.core.models)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}