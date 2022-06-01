plugins {
    id("android-config")
    id("hilt-config")
    id("compose-config")
    id("navigation-config")
}

dependencies {
    implementation(project(":feature:resource"))
    implementation(project(":feature:common"))
    implementation(project(":feature:theme"))

    implementation(project(":feature:memo"))
    implementation(project(":feature:place"))
    implementation(project(":feature:more"))
}