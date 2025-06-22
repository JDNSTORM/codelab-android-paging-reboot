import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.iosTarget
import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.kspIOS

plugins {
    alias(libs.plugins.projectKmp)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.kotlinPluginSerialization)
}
kotlin {
    jvm()
    iosTarget("modelsKit")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.common)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
dependencies {
    "kspCommonMainMetadata"(libs.androidx.room.compiler)
    "kspJvm"(libs.androidx.room.compiler)
    kspIOS(libs.androidx.room.compiler)
}