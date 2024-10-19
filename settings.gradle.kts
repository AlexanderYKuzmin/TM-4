pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
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
include(":core:datastore")
include(":data:remote_fb")
include(":core:network_fb")
include(":feature:api")
include(":feature:site_filter")
include(":feature:site_creation")
include(":feature:home")
