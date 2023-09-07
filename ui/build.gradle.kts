plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

group = "com.linroid.klipperx"
version = "1.0-SNAPSHOT"

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.koin.core)
            }
        }
    }
}

android {
    namespace = "${group}.ui"
}
