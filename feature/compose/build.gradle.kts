plugins {
    id("android-config")
    id("compose-config")
}

dependencies {
    implementation(project(":feature:resource"))
    implementation(project(":feature:theme"))
}