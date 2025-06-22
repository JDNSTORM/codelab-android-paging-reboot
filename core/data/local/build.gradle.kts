import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.iosTarget
import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.kspIOS
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.devtoolsKsp)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.core.data.local"

        withHostTestBuilder {}

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    iosTarget(
        xcfName = "coreDataLocalKit",
        onFramework = {
            it.nativeSqlite()
        }
    )

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
//            implementation(libs.androidx.room.ktx)
            implementation(libs.androidx.room.paging)
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.models)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.appcompat)
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(libs.junit)
            }
        }
    }
}
dependencies {
    "kspCommonMainMetadata"(libs.androidx.room.compiler)
    "kspJvm"(libs.androidx.room.compiler)
    "kspAndroid"(libs.androidx.room.compiler)
    kspIOS(libs.androidx.room.compiler)
}
ksp {
    arg("room.generateKotlin", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

fun Framework.nativeSqlite(){
    // Required when using NativeSQLiteDriver
    linkerOpts.add("-lsqlite3")
}