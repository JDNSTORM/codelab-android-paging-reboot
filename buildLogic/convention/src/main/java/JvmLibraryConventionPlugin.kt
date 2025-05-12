import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.example.android.codelabs.paging.build_logic.convention.configuration.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(ProjectPlugin.JvmLib.id)
            }
            configureKotlinJvm()
        }
    }

    companion object{
        const val PLUGIN_ID = "project.jvm.library"
    }
}