pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "KlipperX"

include(
    ":foundation",
    ":storage",
    ":moonraker",
    ":main",
    ":targets:android",
    ":targets:desktop",
)
