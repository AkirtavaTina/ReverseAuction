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

rootProject.name = "ReverseAuction"
include(":app")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:ui")
include(":core:network")
include(":core:common")
include(":core:feature_api")
include(":feature:home_page")
include(":feature:home_page:ui")
include(":feature:home_page:data")
include(":feature:home_page:domain")
include(":feature:profile_page")
include(":feature:profile_page:data")
include(":feature:profile_page:domain")
include(":feature:profile_page:ui")
