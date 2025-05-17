import com.android.build.api.dsl.androidLibrary
import com.example.android.codelabs.paging.build_logic.convention.ProjectDefaults
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * A Gradle convention plugin for setting up Kotlin Multiplatform projects with an Android library target.
 *
 * This plugin applies the base KMP convention plugin and the Android KMP library plugin.
 * It configures the Android library target with the default compile and min SDK versions.
 *
 * It also configures common dependencies for main and test source sets and sets up Android testing.
 */
class KmpAndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply(ProjectPlugin.KmpConvention.id)
                apply(ProjectPlugin.AndroidKmpLib.id)
            }

            configure<KotlinMultiplatformExtension> {
                androidLibrary {
                    compileSdk = ProjectDefaults.ANDROID_COMPILE_SDK
                    minSdk = ProjectDefaults.ANDROID_MIN_SDK
                }
            }
        }
    }

    companion object {
        const val PLUGIN_ID = "project.kmp.android.library"
    }
}