plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
    kotlin("native.cocoapods")
}

group = "com.linroid.klipperx"
version = "1.0-SNAPSHOT"

kotlin {
    if (isOnMacOs) {
        cocoapods {
            summary = "The main entry module for KlipperX"
            homepage = "https://github.com/linroid/KlipperX"
            source = "https://github.com/linroid/KlipperX"
            authors = "linroid"
            license = "Apache License 2.0"
            ios.deploymentTarget = "15.2"
            podfile = project.file("../targets/ios/Podfile")
            useLibraries()
            framework {
                baseName = "main"
                isStatic = true
            }
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
        }
        named("commonMain") {
            dependencies {
                implementation(project(":foundation"))
                implementation(project(":storage"))
                implementation(project(":moonraker"))
                implementation(project(":ui"))
                implementation(project(":moonraker"))
                api(libs.precompose)
                api(compose.materialIconsExtended)
            }
        }
        if (isOnMacOs) {
            named("iosMain") {
                dependencies {
                    implementation("app.cash.sqldelight:native-driver:${libs.versions.sqldelight.get()}")
                }
            }
        }
    }
}

android {
    android {
        namespace = "${group}.main"
    }
}
