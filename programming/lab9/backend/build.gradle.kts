import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"
}

group = "lab9"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.integration:spring-integration-http")
    implementation("org.springframework.integration:spring-integration-jdbc")
    implementation("org.springframework.integration:spring-integration-jpa")
    implementation("org.springframework.integration:spring-integration-kafka")
    implementation("org.springframework.integration:spring-integration-security")
    implementation("org.springframework.integration:spring-integration-stomp")
    implementation("org.springframework.integration:spring-integration-websocket")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.session:spring-session-data-redis")

    // https://mvnrepository.com/artifact/redis.clients/jedis
    implementation("redis.clients:jedis:4.4.0")



    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.integration:spring-integration-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation(platform("org.testcontainers:testcontainers-bom:1.18.0")) //import bom
    testImplementation("org.testcontainers:testcontainers:1.18.0")
    testImplementation("org.testcontainers:junit-jupiter:1.18.0")
    testImplementation("app.cash.turbine:turbine:0.12.3")
    // https://mvnrepository.com/artifact/org.testcontainers/postgresql
    testImplementation("org.testcontainers:postgresql:1.18.0")
    // https://mvnrepository.com/artifact/org.testcontainers/kafka
    testImplementation("org.testcontainers:kafka:1.18.0")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
