plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        browser {}
    }
    sourceSets {
        val commonMain by getting {

        }
    }
}


dependencies {
   val kotlinxHtmlVersion = ""
}