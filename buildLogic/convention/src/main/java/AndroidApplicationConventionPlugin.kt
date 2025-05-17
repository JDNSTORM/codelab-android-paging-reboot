import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.android.codelabs.paging.build_logic.convention.ProjectDefaults
import com.example.android.codelabs.paging.build_logic.convention.ProjectPlugin
import com.example.android.codelabs.paging.build_logic.convention.configuration.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(ProjectPlugin.AndroidApp.id)
            }

            extensions.configure<BaseAppModuleExtension> {
                defaultConfig {
                    targetSdk = ProjectDefaults.ANDROID_TARGET_SDK
                }

                applicationVariants.all {
                    outputs.mapNotNull {
                        it as? ApkVariantOutputImpl
                    }.forEach { output ->
                        output.outputFileName = buildString {
                            append(rootProject.name)
                            append('_')
                            append(flavorName)
                            versionName?.let {
                                append('_')
                                append(it)
                            }
                            buildType?.let {
                                append('_')
                                append(it.name)
                            }
                            append(".apk")
                        }
                    }
                }
                configureKotlinAndroid(this)
            }
        }
    }

    companion object{
        const val PLUGIN_ID = "project.android.application"
    }
}