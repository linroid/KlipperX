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
        val koinVersion = "3.3.3"
        named("commonMain") {
            dependencies {
                api("io.insert-koin:koin-core:$koinVersion")
            }
        }
        named("commonTest") {
            dependencies {
                api("io.insert-koin:koin-test:$koinVersion")
                api("io.insert-koin:koin-test-junit4:$koinVersion")
                api("io.insert-koin:koin-test-junit5:$koinVersion")
            }
        }
    }
}
