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
    }
}

rootProject.name = "EffectiveMobileTest"
include(":app")
include(":core")


include(":data")
include(":data:network")
include(":data:database")
include(":feature_favorite")
include(":core_ui")
include(":data:repository")
include(":feature_find_vacancy")
include(":feature_auth")
