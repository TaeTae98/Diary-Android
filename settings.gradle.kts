pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Diary"
include(
    ":app",
    ":feature:resource", ":feature:compose",
    ":feature:theme",
    ":feature:main",
    ":feature:memo",
)
