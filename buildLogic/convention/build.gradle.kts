import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
}
group = "com.example.android.codelabs.paging.build_logic.convention"
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}
tasks.compileKotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_1
    }
}
dependencies {
    compileOnly(libs.androidToolsBuildGradle)
    compileOnly(libs.androidToolsCommon)
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.composeCompilerGradlePlugin)
    compileOnly(libs.kspGradlePlugin)
    compileOnly(libs.kmpGradlePlugin)
    compileOnly(libs.cmpGradlePlugin)
    compileOnly(libs.androidKmpLibraryGradlePlugin)
}
gradlePlugin {
    plugins {
        register("androidApplication"){
            id = "project.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "project.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary"){
            id = "project.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "project.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("jvmLibrary"){
            id = "project.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("kmp"){
            id = "project.kmp"
            implementationClass = "KmpConventionPlugin"
        }
        register("cmp"){
            id = "project.cmp"
            implementationClass = "CmpConventionPlugin"
        }
        register("kmpAndroidLibrary"){
            id = "project.kmp.android.library"
            implementationClass = "KmpAndroidLibraryConventionPlugin"
        }
        register("kmpAndroidPlatformLibrary"){
            id = "project.kmp.android.library.platform"
            implementationClass = "KmpAndroidPlatformLibraryConventionPlugin"
        }
    }
}