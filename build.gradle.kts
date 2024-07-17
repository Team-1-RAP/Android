// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kspPlugins) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("land.sungbin.dependency.graph.plugin") version "1.1.0"
}

dependencyGraphConfig{
    projectName = null
    outputFormat = OutputFormat.PNG
    dependencyBuilder {
        null
    }
}