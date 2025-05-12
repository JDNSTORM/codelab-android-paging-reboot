plugins {
    alias(libs.plugins.projectJvmLibrary)
    alias(libs.plugins.devtoolsKsp)
}
dependencies {
    implementation(libs.androidx.room.common)
    implementation(libs.retrofit.converter.gson)
    ksp (libs.androidx.room.compiler)
}