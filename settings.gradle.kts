pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PRESERVE_ON_USE) // 👈 Fixed version syntax!
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WhiskerPaws"
include(":app")
