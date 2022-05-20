plugins {
    id("android-config")
    id("compose-config")
    id("navigation-config")
}

dependencies {
    implementation(project(":domain"))

    implementation(project(":feature:resource"))
    implementation(project(":feature:common"))
    implementation(project(":feature:compose"))
    implementation(project(":feature:theme"))
}