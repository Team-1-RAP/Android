plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.team1.simplebank.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        buildConfigField("String", "BASE_URL", "\"https://simplebank-stg.koyeb.app/api/\"",)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":xdomain"))
    implementation(project(":common"))

    //api : retrofit - okhttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    //dataStore
    implementation(libs.androidx.datastore)

    //coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test)

    //jwtDecoder
    implementation (libs.jwtdecode)

    //di-hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}