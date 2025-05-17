import com.example.android.codelabs.paging.build_logic.convention.ProjectDefaults
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.example.android.codelabs.paging.build_logic.convention.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


/**
 * A Gradle convention plugin for applying the necessary configurations for a KMP Android platform library module.
 *
 * This plugin applies the following plugins:
 * - [ProjectPlugin.KmpConvention] for standard KMP project configurations.
 * - [ProjectPlugin.AndroidLibConvention] for standard Android library configurations.
 *
 * It also configures the [KotlinMultiplatformExtension] to include the Android target.
 */
class KmpAndroidPlatformLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply(ProjectPlugin.KmpConvention.id)
                apply(ProjectPlugin.AndroidLib.id)
            }

            configure<KotlinMultiplatformExtension> {
                androidTarget()
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                configureKotlinAndroid(this)
            }
        }
    }

    companion object {
        const val PLUGIN_ID = "project.kmp.android.library.platform"
    }
}