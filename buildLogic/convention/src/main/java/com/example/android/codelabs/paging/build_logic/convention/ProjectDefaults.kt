package com.example.android.codelabs.paging.build_logic.convention

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object ProjectDefaults {

    const val ANDROID_MIN_SDK = 24
    const val ANDROID_TARGET_SDK = 35
    const val ANDROID_COMPILE_SDK = ANDROID_TARGET_SDK

    const val JVM_VERSION = 21
    val JAVA_SOURCE_COMPATIBILITY: JavaVersion = JavaVersion.VERSION_21
    val JAVA_TARGET_COMPATIBILITY: JavaVersion = JAVA_SOURCE_COMPATIBILITY
    val JVM_TARGET: JvmTarget = JvmTarget.JVM_21
}