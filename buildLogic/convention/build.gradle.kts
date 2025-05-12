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

}
gradlePlugin {
    plugins {

    }
}