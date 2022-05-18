import org.gradle.api.JavaVersion

object Config {
    const val COMPILE_SDK = 32
    const val MIN_SDK = 31
    const val TARGET_SDK = 32

    val SOURCE_COMPATIBILITY = JavaVersion.VERSION_11
    val TARGET_COMPATIBILITY = JavaVersion.VERSION_11
    val JVM_TARGET = JavaVersion.VERSION_11.toString()

    const val VERSION_CODE = 10000
    const val VERSION_NAME = "1.00.00"

    const val APPLICATION_ID = "com.taetae98.diary"
}