plugins {
    id("multiplatform-setup")
}

kotlin {
    sourceSets {
        val ktorVersion = "2.2.3"
        named("commonMain") {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-websockets:$ktorVersion")
            }
        }
        named("commonTest") {
            dependencies {
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
            }
        }
    }
}
