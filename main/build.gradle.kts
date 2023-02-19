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
            summary = "KotlinX Compose Multiplatform Shared Module"
            homepage = "https://github.com/linroid/KlipperX"
            ios.deploymentTarget = "15.2"
            podfile = project.file("../targets/ios/Podfile")
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
                implementation(project(":storage"))
                implementation(project(":moonraker"))
            }
        }
    }
}

android {
    namespace = "com.linroid.klipperx.main"
}
