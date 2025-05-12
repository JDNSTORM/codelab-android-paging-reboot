plugins {
    alias(libs.plugins.projectAndroidLibrary)
}

android {
    namespace = "com.example.android.codelabs.paging.core.repositories"

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
    implementation (libs.androidx.paging.runtime.ktx)
    implementation(libs.retrofit)
    implementation(projects.core.data.local)
    implementation(projects.core.data.network)
    api(projects.core.models)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}