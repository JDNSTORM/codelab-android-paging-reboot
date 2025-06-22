package com.example.android.codelabs.paging.build_logic.convention.kmp_extensions

import org.gradle.api.Action
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests

/**
 * Configures the iOS targets for a Kotlin Multiplatform project.
 *
 * This function creates and configures the following iOS targets:
 * - `iosX64`: For running on Intel-based iOS simulators.
 * - `iosArm64`: For running on ARM64-based iOS devices.
 * - `iosSimulatorArm64`: For running on ARM64-based iOS simulators.
 *
 * It also sets up the framework name and optionally the linkage type (static or dynamic) for each target.
 *
 * @param xcfName The base name for the XCFramework that will be produced.
 * @param static If `true`, the framework will be linked statically. Defaults to `false` (dynamic linking).
 * @param configureX64 An optional [Action] to further configure the `iosX64` target.
 * @param configureArm64 An optional [Action] to further configure the `iosArm64` target.
 * @param configureSimulatorArm64 An optional [Action] to further configure the `iosSimulatorArm64` target.
 */
fun KotlinMultiplatformExtension.iosTarget(
    xcfName: String,
    static: Boolean = false,
    configureX64: Action<KotlinNativeTargetWithSimulatorTests> = Action {  },
    configureArm64: Action<KotlinNativeTarget> = Action {  },
    configureSimulatorArm64: Action<KotlinNativeTargetWithSimulatorTests> = Action {  }
){
    listOf(
        iosX64(configure = configureX64),
        iosArm64(configure = configureArm64),
        iosSimulatorArm64(configure = configureSimulatorArm64),
    ).forEach { target ->
        target.binaries.framework {
            baseName = xcfName
            isStatic = static
        }
    }
}