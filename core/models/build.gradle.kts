plugins {
    alias(libs.plugins.projectJvmLibrary)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.kotlinPluginSerialization)
}
dependencies {
    implementation(libs.androidx.room.common)
    implementation(libs.retrofit.converter.gson)
    ksp (libs.androidx.room.compiler)
    implementation(libs.kotlinx.serialization.json)
}