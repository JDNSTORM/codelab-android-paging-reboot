plugins {
    alias(libs.plugins.projectKmpAndroidLibrary)
    alias(libs.plugins.projectCmp)
}
kotlin {
    jvm("desktop")
    androidLibrary {
        namespace = "com.example.android.codelabs.paging.core.designsystem"

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        val desktopMain by getting
        commonMain.dependencies {
            api(compose.ui)
//            api(compose.ui.graphics)
            api(compose.material3)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.appcompat)
            api(libs.androidx.activity.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        getByName("desktopTest").dependencies {
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
//android {
//    namespace = "com.example.android.codelabs.paging.core.designsystem"
//
//    defaultConfig {
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//}
//
//dependencies {
//    implementation(libs.androidx.core)
//    implementation(libs.androidx.appcompat)
//    api(libs.androidx.activity.compose)
//    api(libs.compose.ui)
//    api(libs.compose.ui.graphics)
//    api(libs.compose.material3)
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//}