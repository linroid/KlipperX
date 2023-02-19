group "com.linroid.klipperx"
version "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    // val kotlinVersion = libs.version.kotlin.get()
    // val agpVersion = libs.version.agp.get()
    // val composeVersion = libs.version.compose.get()
    //
    // kotlin("multiplatform").version(kotlinVersion)
    // kotlin("android").version(kotlinVersion)
    // kotlin("plugin.serialization").version(kotlinVersion)
    // id("com.android.application").version(agpVersion)
    // id("com.android.library").version(agpVersion)
    // id("org.jetbrains.compose").version(composeVersion)

    // alias(libs.plugins.android.application) apply false
    // alias(libs.plugins.android.library) apply false
    // alias(libs.plugins.kotlin.android) apply false
}