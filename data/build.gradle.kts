plugins {
    id("android-config")
    id("hilt-config")
    id("room-config")
}

android {
    defaultConfig {
        buildConfigField("String", "DIARY_DATABASE_NAME", "\"diary_database.db\"")

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

    implementation(project(":feature:base"))
}