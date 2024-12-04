pluginManagement {
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
        maven("https://jitpack.io")
    }
}

rootProject.name = "Reusable components"
include(":app")
include(":adapter")
include(":viewpageradapter")
include(":permissions")
include(":reusableapi")
include(":media-picker")
include(":payment-app")
include(":ml-kit")
include(":loaders")
include(":retrofitexample")
include(":GenericForm")
