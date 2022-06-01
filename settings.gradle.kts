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
        maven {
            setUrl("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}

rootProject.name = "Diary"
include(
    ":app", ":domain", ":data",
)

include(
    ":feature:resource", ":feature:common",
)

include(
    ":feature:theme", ":feature:compose",
)

include(
    ":feature:main",
    ":feature:memo", ":feature:place",
)

include(
    ":feature:more",
    ":feature:setting",
    ":feature:developer"
)
