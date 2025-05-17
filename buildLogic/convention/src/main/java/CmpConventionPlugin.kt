import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * This plugin applies the KMP convention plugin and the Compose Multiplatform plugin.
 * It also adds the Compose runtime dependency to the commonMain source set.
 */
class CmpConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply(ProjectPlugin.KmpConvention.id)
                apply(ProjectPlugin.Compose.id)
                apply(ProjectPlugin.ComposeMultiplatform.id)
            }
            val composeDependencies = ComposePlugin.Dependencies(target)

            configure<KotlinMultiplatformExtension> {
                sourceSets.apply {
                    commonMain {
                        dependencies {
                            implementation(composeDependencies.runtime)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val PLUGIN_ID = "project.cmp"
    }
}