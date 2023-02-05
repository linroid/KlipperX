group "com.linroid.klipperx"
version "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// see https://discuss.kotlinlang.org/t/disabling-androidandroidtestrelease-source-set-in-gradle-kotlin-dsl-script/21448
subprojects {
    afterEvaluate {
        project.extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()?.let { ext ->
            ext.sourceSets {
                // Workaround for:
                //
                // The Kotlin source set androidXXXXX was configured but not added to any
                // Kotlin compilation. You can add a source set to a target's compilation by connecting it
                // with the compilation's default source set using 'dependsOn'.
                // See https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#connecting-source-sets
                sequenceOf("AndroidTest", "TestFixtures").forEach { artifact ->
                    sequenceOf("", "Release", "Debug").forEach { variant ->
                        findByName("android$artifact$variant")
                            ?.let(::remove)
                    }
                }
            }
        }
    }
}
plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}