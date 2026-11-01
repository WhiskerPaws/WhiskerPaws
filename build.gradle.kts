buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

// 👈 THE ALLPROJECTS BLOCK WAS REMOVED FROM HERE TO SATISFY SETTINGS REQUIREMENTS

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
