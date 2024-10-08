
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
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
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
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)

    }

}