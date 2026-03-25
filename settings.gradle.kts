pluginManagement {
    includeBuild("build-logic") // This allows the main project to use plugins defined in build-logic
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ratio Calc"
include(":app")
include(":feature:calculator")
include(":feature:info")
include(":core:navigation")
include(":core:designsystem")
include(":core:common")
