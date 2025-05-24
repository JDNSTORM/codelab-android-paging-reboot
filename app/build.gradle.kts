/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    alias(libs.plugins.projectCmp)
    alias(libs.plugins.projectAndroidApplicationCompose)
}
kotlin {
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            implementation (libs.kotlinx.coroutines.core)
            implementation (libs.androidx.lifecycle.runtime)
            implementation (libs.androidx.lifecycle.viewmodel)
            implementation (libs.androidx.lifecycle.viewmodel.compose)
            implementation (libs.androidx.lifecycle.viewmodel.savedstate)
            implementation(compose.ui)
//            api(compose.ui.graphics)
            implementation(compose.material3)

            implementation(projects.core.models)
            implementation(projects.core.data.network)
            implementation(projects.core.data.local)
            implementation(projects.core.repositories)
            implementation(projects.core.designSystem)
            implementation(projects.pagingCompose)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.compose.viewmodel)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core)
            implementation(libs.androidx.appcompat)
            implementation(libs.material)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation (libs.kotlinx.coroutines.android)
            implementation (libs.androidx.lifecycle.viewmodel.ktx)
        }
        androidInstrumentedTest {
            dependencies {
                implementation(libs.androidx.junit)
                implementation(libs.androidx.espresso.core)
            }
        }
    }
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }
}
android {
    namespace = "com.example.android.codelabs.paging"
    defaultConfig {
        applicationId = "com.example.android.codelabs.paging"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("debug")
        }
    }
}
