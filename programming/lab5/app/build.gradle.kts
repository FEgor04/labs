plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    application
    id("com.adarshr.test-logger") version "3.2.0"
    id("org.jetbrains.dokka") version "1.6.0"
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

//    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")


    val mockkVersion = "1.13.2"
    testImplementation("io.mockk:mockk:${mockkVersion}")
}

application {
    mainClass.set("lab5.AppKt")
}

/**
 * Сбор jar файла со всеми зависимостями (Kotlin..)
 */
tasks.register<Jar>("fatJar") {
    dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
    archiveClassifier.set("app") // Naming the jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
    val sourcesMain = sourceSets.main.get()
    val contents = configurations.runtimeClasspath.get()
        .map { if (it.isDirectory) it else zipTree(it) } +
            sourcesMain.output
    from(contents)
}

/**
 * build вызывает сборку fatJar и генерацию dokkaHtml-документации
 */
tasks.build {
    finalizedBy(tasks.getByPath("fatJar").path, tasks.dokkaHtml)
}

/**
 * Зависимость от задачи build
 */
tasks.dokkaHtml.configure {
    dependsOn(tasks.build)
    outputDirectory.set(buildDir.resolve("dokka"))
}

/**
 * При тестах генерируется jacoco-отчет о покрытии
 */
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

/**
 * Генерация html отчета в папку jacocoHtml
 */
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}