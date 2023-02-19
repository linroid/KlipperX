plugins {
    id("multiplatform-setup")
    id("app.cash.sqldelight")
}

group = "com.linroid.klipperx"
version = "1.0.0-SNAPSHOT"

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.linroid.klipperx.db")
        }
    }
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":foundation"))
            }
        }
        named("androidMain") {
            dependencies {
                implementation("app.cash.sqldelight:android-driver:2.0.0-alpha05")
            }
        }
        named("iosMain") {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0-alpha05")
            }
        }
        named("desktopMain") {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
            }
        }
    }
}
