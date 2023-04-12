plugins {
    id("lab8.kotlin-common-conventions")
}

tasks.dokkaHtmlMultiModule {
    moduleName.set("Лабораторная работа #8")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    tasks {
        withType<JavaCompile>().all {
            sourceCompatibility = "11"
            targetCompatibility = "11"
        }
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}