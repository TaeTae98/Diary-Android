import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        applicationId = Config.APPLICATION_ID
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.VERSION_CODE
        versionName = Config.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        manifestPlaceholders["NAVER_MAP_CLIENT_ID"] = gradleLocalProperties(rootDir).getProperty("NAVER_MAP_CLIENT_ID")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("keystore/debug.keystore")
            keyAlias = gradleLocalProperties(rootDir).getProperty("DEBUG_KEY_ALIAS")
            storePassword = gradleLocalProperties(rootDir).getProperty("DEBUG_KEY_STORE_PASSWORD")
            keyPassword = gradleLocalProperties(rootDir).getProperty("DEBUG_KEY_PASSWORD")
        }

        create("release") {
            storeFile = file("keystore/release.keystore")
            keyAlias = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_ALIAS")
            storePassword = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_STORE_PASSWORD")
            keyPassword = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("real") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".real"
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = Config.SOURCE_COMPATIBILITY
        targetCompatibility = Config.TARGET_COMPATIBILITY
    }

    kotlinOptions {
        jvmTarget = Config.JVM_TARGET
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(project(":feature:resource"))
    implementation(project(":feature:common"))
    implementation(project(":feature:main"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("com.google.dagger:hilt-android:2.41")
    kapt("com.google.dagger:hilt-android-compiler:2.41")
}