package com.example.android.codelabs.paging.build_logic.convention.configuration

import com.example.android.codelabs.paging.build_logic.convention.CommonExt
import com.example.android.codelabs.paging.build_logic.convention.ProjectDefaults
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExt
) {
    commonExtension.apply {
        compileSdk = ProjectDefaults.ANDROID_COMPILE_SDK

        defaultConfig {
            minSdk = ProjectDefaults.ANDROID_MIN_SDK
        }

        compileOptions {
            sourceCompatibility = ProjectDefaults.JAVA_SOURCE_COMPATIBILITY
            targetCompatibility = ProjectDefaults.JAVA_TARGET_COMPATIBILITY
        }
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = ProjectDefaults.JAVA_SOURCE_COMPATIBILITY
        targetCompatibility = ProjectDefaults.JAVA_TARGET_COMPATIBILITY
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(ProjectDefaults.JVM_TARGET)
        }
    }
}