pluginManagement {
    repositories {
        google()
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

rootProject.name = "TM-4"
include(":app")
include(":feature:sites")
include(":feature:measurements")
include(":feature:report")
include(":common")
include(":data:remote")
include(":data:local")

include(":core:network")
include(":core:database")
include(":feature:login")
