plugins {
    id("android-config")
    id("hilt-config")
    id("room-config")
}

dependencies {
    implementation(project(":feature:common"))
}