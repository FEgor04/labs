plugins {
    kotlin("multiplatform") version "1.8.20" apply false
    kotlin("plugin.serialization") version "1.8.20"
}

allprojects {
    version = "0.1.1"

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }
}

tasks.register<Copy>("copyTypings") {
    dependsOn(":common:jsBrowserProductionLibraryDistribution")
        from("build/js/packages/lab9-common/kotlin") {
            include("*.d.ts")
        }

        into("frontend/src/generated")
    }
