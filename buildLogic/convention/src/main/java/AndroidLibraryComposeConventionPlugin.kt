import com.android.build.api.dsl.LibraryExtension
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.example.android.codelabs.paging.build_logic.convention.configuration.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(ProjectPlugin.AndroidLibConvention.id)
                apply(ProjectPlugin.Compose.id)
            }
            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }

    companion object{
        const val PLUGIN_ID = "project.android.library.compose"
    }
}