plugins {
    id("multiplatform-setup")
}

group = "com.linroid.klipperx"
version = "1.0.0-SNAPSHOT"

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.koin.core)
                api(libs.napier)
            }
        }
        named("commonTest") {
            dependencies {
                api(libs.bundles.koin.tests)
            }
        }

        named("androidMain") {
            dependencies {
                api(libs.androidx.core)
            }
        }
    }
}

android {
    namespace = "${group}.foundation"
}
