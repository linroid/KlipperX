import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("multiplatform-setup")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        getByName("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                // @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                // api(compose.material3)
                api(compose.ui)
            }
        }

        getByName("desktopMain") {
            dependencies {
                api(compose.preview)
            }
        }
    }
}