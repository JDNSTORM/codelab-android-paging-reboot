plugins {
    alias(libs.plugins.projectAndroidLibraryCompose)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.example.android.codelabs.paging.core.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    api(libs.androidx.activity.compose)
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}