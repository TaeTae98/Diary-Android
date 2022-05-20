plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.foundation:foundation:1.1.1")
    implementation("androidx.compose.material:material-icons-core:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")
}