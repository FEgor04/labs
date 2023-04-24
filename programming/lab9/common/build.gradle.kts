plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        moduleName="lab9"
        browser {
            webpackTask {
                output.library = "lab9"
            }
        }
        binaries.library()
        generateTypeScriptDefinitions()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
    }
}
