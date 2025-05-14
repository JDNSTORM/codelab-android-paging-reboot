plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.kotlinPluginSerialization)
}
kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.common)
            implementation(libs.kotlinx.serialization.json)
            api(projects.core.common)
        }
    }
}
dependencies {
    "kspCommonMainMetadata"(libs.androidx.room.compiler)
    "kspJvm"(libs.androidx.room.compiler)
}