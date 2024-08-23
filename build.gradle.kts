
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kspPlugins) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("org.jetbrains.kotlin.kapt") version "1.9.24" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false

    id("land.sungbin.dependency.graph.plugin") version "1.1.0"
}

dependencyGraphConfig{
    projectName = null
    outputFormat = OutputFormat.PNG
    dependencyBuilder {
        null
    }
}

buildscript {
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
        val nav_version = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

    }

}