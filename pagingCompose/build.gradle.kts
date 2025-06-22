import com.example.android.codelabs.paging.build_logic.convention.kmp_extensions.iosTarget

plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.projectCmp)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.compose"
    }
    iosTarget("pagingComposeKit")
    applyDefaultHierarchyTemplate()

    sourceSets {
        val nonAndroidMain by creating {
            dependsOn(commonMain.get())
        }
        androidMain.dependencies {
            implementation(libs.androidx.paging.compose)
        }
        commonMain.dependencies {
            api(libs.androidx.paging.common)
            api(libs.kotlinx.coroutines.core)
            api(compose.foundation)
        }
        jvmMain {
            dependsOn(nonAndroidMain)
        }
        iosMain {
            dependsOn(nonAndroidMain)
        }
    }
}
