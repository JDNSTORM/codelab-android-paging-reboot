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
    alias(libs.plugins.projectAndroidApplicationCompose)
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.devtoolsKsp)
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

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }

    buildFeatures {
        viewBinding = true
    }
}

ksp {
    arg("room.generateKotlin", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation (fileTree("libs"))
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.androidx.appcompat)
    implementation (libs.recyclerview)
    implementation (libs.constraintlayout)
    implementation (libs.material)

    // architecture components
    implementation (libs.androidx.core)
    implementation (libs.androidx.lifecycle.runtime)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.savedstate)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.paging)
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.material3)
    ksp (libs.androidx.room.compiler)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.gson)
    implementation (libs.retrofit.mock)
    implementation (libs.logging.interceptor)

    implementation(projects.core.models)
    implementation(projects.core.data.network)
    implementation(projects.core.data.local)
}
