plugins {
    id("android-setup")
    id("kotlin-multiplatform")
}
kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(11)
        testRuns["test"].executionTask.configure {
            useJUnit()
            reports {
                junitXml.required.set(true)
                html.required.set(true)
            }
        }
    }
    // Only config ios on macos system or we will get errors when building on aarch64 linux
    // see https://youtrack.jetbrains.com/issue/KT-36871
    if (isOnMacOs) {
        ios()
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            }
        }

        named("desktopMain") {
            dependencies {
                implementation("org.slf4j:slf4j-api:1.7.30")
                implementation("org.slf4j:slf4j-simple:1.7.30")
            }
        }

        named("androidUnitTest") {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }

        if (isOnMacOs) {
            val iosMain by getting { dependsOn(commonMain) }
            val iosTest by getting { dependsOn(commonTest) }
            getByName("iosX64Main") { dependsOn(iosMain) }
            getByName("iosX64Test") { dependsOn(iosTest) }
            getByName("iosArm64Main") { dependsOn(iosMain) }
            getByName("iosArm64Test") { dependsOn(iosTest) }
            getByName("iosSimulatorArm64Main") { dependsOn(iosMain) }
            getByName("iosSimulatorArm64Test") { dependsOn(iosTest) }
        }
    }
}

tasks {
    withType<Test> {
        testLogging {
            outputs.upToDateWhen { false }
            // showStandardStreams = true
        }
    }
}