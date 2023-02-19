plugins {
    id("multiplatform-setup")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.optIn("kotlinx.coroutines.DelicateCoroutinesApi")
        }
        named("commonMain") {
            dependencies {
                implementation(project(":foundation"))
                implementation(libs.bundles.ktor)
            }
        }
        named("commonTest") {
            dependencies {
                implementation(libs.ktor.mock)
            }
        }
        named("desktopMain") {
            dependencies {
                implementation("org.slf4j:slf4j-api:1.7.36")
                implementation("org.slf4j:slf4j-simple:1.7.36")
            }
        }
    }
}

android {
    namespace = "${group}.moonraker"
}