import com.android.build.api.dsl.LibraryExtension
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.example.android.codelabs.paging.build_logic.convention.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(ProjectPlugin.AndroidLib.id)
                apply(ProjectPlugin.KotlinAndroid.id)
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

    companion object{
        const val PLUGIN_ID = "project.android.library"
    }
}