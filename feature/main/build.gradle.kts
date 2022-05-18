plugins {
    id("android-config")
    id("hilt-config")
    id("compose-config")
    id("navigation-config")
}

dependencies {
    implementation(project(":feature:theme"))
    implementation(project(":feature:memo"))
}