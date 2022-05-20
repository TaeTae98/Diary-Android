plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    testOptions.unitTests.all {
        it.useJUnitPlatform()
    }
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:4.3.2")
}