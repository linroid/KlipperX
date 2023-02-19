import java.util.Properties
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val props = Properties()
file("../gradle.properties").reader().use { props.load(it) }

dependencies {
    implementation("org.jetbrains.compose:compose-gradle-plugin:${props["compose.version"] as String}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${props["kotlin.version"] as String}")
    implementation("com.android.tools.build:gradle:${props["agp.version"] as String}")
}
