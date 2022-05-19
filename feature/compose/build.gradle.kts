plugins {
    id("android-config")
    id("compose-config")
}

dependencies {
    implementation(project(":feature:theme"))
    implementation(project(":feature:common"))
}