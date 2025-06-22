import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.iosTarget

plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.core.repositories"

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    iosTarget("coreRepositoriesKit")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.retrofit)
        }
        commonMain.dependencies {
            implementation (libs.androidx.paging.common)
            implementation(projects.core.data.local)
            implementation(projects.core.data.network)
            api(projects.core.models)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        jvmTest.dependencies {
            implementation(libs.junit)
        }
        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.junit)
                implementation(libs.androidx.espresso.core)
            }
        }
    }
}