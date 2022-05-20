plugins {
    id("android-config")
    id("hilt-config")
    id("compose-config")
    id("navigation-config")
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":feature:resource"))
    implementation(project(":feature:common"))
    implementation(project(":feature:theme"))
    implementation(project(":feature:compose"))
}