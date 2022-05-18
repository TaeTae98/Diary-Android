plugins {
    id("dependency-config")
}

dependencies {
    implementation(project(":feature:resource"))
    implementation(project(":feature:theme"))
}