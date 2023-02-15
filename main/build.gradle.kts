plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
    kotlin("native.cocoapods")
}

group = "com.linroid.klipperx"
version = "1.0-SNAPSHOT"

kotlin {
    cocoapods {
        summary = "KotlinX Compose Multiplatform Shared Module"
        homepage = "https://github.com/linroid/KlipperX"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../targets/ios/Podfile")
        framework {
            baseName = "main"
            isStatic = true
        }
    }
}