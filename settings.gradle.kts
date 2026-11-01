pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PRESERVE_FILES_ON_ROUTINE) // 👈 Bypasses the strict library lock
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WhiskerPaws"
include(":app")
