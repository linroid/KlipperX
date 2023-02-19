plugins {
    id("multiplatform-setup")
    kotlin("native.cocoapods")
    alias(libs.plugins.sqldelight)
}

group = "com.linroid.klipperx"
version = "1.0.0-SNAPSHOT"

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.linroid.klipperx.storage.db")
        }
    }
    linkSqlite.set(true)
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":foundation"))
                api(libs.settings)
            }
        }
        named("androidMain") {
            dependencies {
                implementation("app.cash.sqldelight:android-driver:${libs.versions.sqldelight.get()}")
            }
        }
        if (isOnMacOs) {
            named("iosMain") {
                dependencies {
                    implementation("app.cash.sqldelight:native-driver:${libs.versions.sqldelight.get()}")
                }
            }
        }
        named("desktopMain") {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:${libs.versions.sqldelight.get()}")
            }
        }
    }
}

android {
    namespace = "${group}.storage"
}