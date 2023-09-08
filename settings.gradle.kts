pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

 plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "KlipperX"

include(
    ":foundation",
    ":ui",
    ":storage",
    ":moonraker",
    ":main",
    ":targets:android",
    ":targets:desktop",
)
