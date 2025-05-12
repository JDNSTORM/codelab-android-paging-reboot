import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.example.android.codelabs.paging.build_logic.convention.get
import com.example.android.codelabs.paging.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(ProjectPlugin.AndroidAppConvention.id)
                apply(ProjectPlugin.Compose.id)
            }

            extensions.configure<BaseAppModuleExtension> {
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
    }

    companion object{
        const val PLUGIN_ID = "project.android.application.compose"
    }
}