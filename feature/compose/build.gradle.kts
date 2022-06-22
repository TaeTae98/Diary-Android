plugins {
    id("android-config")
    id("compose-config")
    id("navigation-config")
    id("hilt-config")
    id("map-config")
    id("paging-config")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":feature:resource"))
    implementation(project(":feature:theme"))
    implementation(project(":feature:common"))
}