plugins {
    id("android-config")
    id("compose-config")
    id("navigation-config")
}

dependencies {
    implementation(project(":feature:resource"))
    implementation(project(":feature:theme"))
    implementation(project(":feature:common"))
}