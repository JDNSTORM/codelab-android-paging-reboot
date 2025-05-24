plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.projectCmp)
}
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.compose"
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.paging.compose)
            api(libs.compose.foundation)
        }
        commonMain.dependencies {
            api(libs.androidx.paging.common)
            api(libs.kotlinx.coroutines.core)
        }
    }
}
