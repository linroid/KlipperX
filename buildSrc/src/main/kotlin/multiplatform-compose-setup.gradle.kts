import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.animation)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                // @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                // api(compose.material3)
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.preview)
            }
        }
    }
}