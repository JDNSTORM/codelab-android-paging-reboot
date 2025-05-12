plugins {
    alias(libs.plugins.projectJvmLibrary)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.kotlinPluginSerialization)
}
dependencies {
    implementation(libs.androidx.room.common)
    ksp (libs.androidx.room.compiler)
    implementation(libs.kotlinx.serialization.json)
}