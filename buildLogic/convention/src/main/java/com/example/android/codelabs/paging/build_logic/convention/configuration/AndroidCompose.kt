package com.example.android.codelabs.paging.build_logic.convention.configuration

import com.example.android.codelabs.paging.build_logic.convention.CommonExt
import com.example.android.codelabs.paging.build_logic.convention.get
import com.example.android.codelabs.paging.build_logic.convention.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExt){
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        dependencies {
            "implementation"(platform(libs["compose.bom"]))
            "implementation"(libs["compose.runtime"])
            "implementation"(libs["compose.ui.tooling.preview"])
            "debugImplementation"(libs["compose.ui.tooling"])
            "debugImplementation"(libs["compose.ui.test.manifest"])
            "androidTestImplementation"(platform(libs["compose.bom"]))
            "androidTestImplementation"(libs["compose.ui.test.junit4"])
        }
    }
}