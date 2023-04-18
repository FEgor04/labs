plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        moduleName = "lab9"
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                webpackTask {
                    outputFileName = "lab9.js"
                }
            }
        }
        binaries.executable()

    }
}

val kotlinWrappersVersion = "1.0.0-pre.538"


fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

val ktor_version = "2.2.4"


dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))

    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("react-router-dom"))

    implementation(kotlinw("emotion"))
    implementation(kotlinw("mui"))
    implementation(kotlinw("mui-icons"))

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-js:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-resources:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation(npm("mobx", "6.9.0"))
    implementation(npm("mobx-react", "7.6.0"))


    implementation(project(":common"))
}

// Heroku Deployment (chapter 9)
tasks.register("stage") {
    dependsOn("build")
}

