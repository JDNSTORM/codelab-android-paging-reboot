package com.example.android.codelabs.paging.build_logic.convention.kmp_extensions

import org.gradle.api.Action
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
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
    configureSimulatorArm64: Action<KotlinNativeTargetWithSimulatorTests> = Action {  },
    onFramework: (Framework) -> Unit = {}
){
    listOf(
        iosX64(configure = configureX64),
        iosArm64(configure = configureArm64),
        iosSimulatorArm64(configure = configureSimulatorArm64),
    ).forEach { target ->
        target.binaries.framework {
            baseName = xcfName
            isStatic = static
            onFramework(this)
        }
    }
}

/**
 * Adds KSP (Kotlin Symbol Processing) dependencies for all configured iOS targets.
 *
 * This function is an extension for `DependencyHandlerScope` and is typically used within a
 * `dependencies` block in a Gradle build script. It applies the provided KSP dependency
 * to the following iOS target-specific KSP configurations:
 * - `kspIosX64`
 * - `kspIosArm64`
 * - `kspIosSimulatorArm64`
 *
 * This ensures that KSP runs for each of the iOS targets defined by the `iosTarget` function.
 *
 * @param provider A `Provider` for the KSP dependency artifact (e.g., `libs.ksp.compiler`).
 */
fun DependencyHandlerScope.kspIOS(provider: Provider<MinimalExternalModuleDependency>){
    "kspIosX64"(provider)
    "kspIosArm64"(provider)
    "kspIosSimulatorArm64"(provider)
}