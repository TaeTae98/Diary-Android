import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("android-config")
    id("hilt-config")
    id("data-store-config")
    id("room-config")
    id("retrofit-config")
    id("serialization-config")
}

android {
    defaultConfig {
        buildConfigField("String", "DIARY_DATABASE_NAME", "\"diary_database.db\"")
        buildConfigField("String", "NAVER_CLIENT_ID", "\"${gradleLocalProperties(rootDir).getProperty("NAVER_CLIENT_ID")}\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"${gradleLocalProperties(rootDir).getProperty("NAVER_CLIENT_SECRET")}\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "DIARY_DATABASE_NAME", "\"diary_database_debug.db\"")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":feature:common"))
}