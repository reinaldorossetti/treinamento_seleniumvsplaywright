import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    application
    kotlin("jvm") version "1.7.0"
    id("io.qameta.allure") version "2.8.1"
    id("org.gradle.test-retry") version "1.3.1"
    java
}

group "com.seleniumvscypress"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.hamcrest:hamcrest:2.2")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
// https://mvnrepository.com/artifact/org.testng/testng
    implementation("org.testng:testng:7.11.0")
    implementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:4.4.0")
    testImplementation("io.rest-assured:json-schema-validator:4.4.0")
    testImplementation("com.github.javafaker:javafaker:1.0.2")
    testImplementation("io.qameta.allure:allure-junit5:2.20.1")
    implementation("io.qameta.allure:allure-okhttp3:2.16.1")

    testImplementation("io.qameta.allure:allure-kotlin-model:2.2.7")
    testImplementation("io.qameta.allure:allure-kotlin-commons:2.2.7")
    testImplementation("io.qameta.allure:allure-kotlin-junit4:2.2.7")

    // frameworks de teste.
    // https://mvnrepository.com/artifact/io.appium/java-client
    implementation("io.appium:java-client:8.1.0")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.1.0")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-chrome-driver
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.0.0")
    // https://mvnrepository.com/artifact/com.applitools/eyes-selenium-java3
    implementation("com.applitools:eyes-selenium-java3:3.210.6")
    // https://central.sonatype.com/artifact/com.microsoft.playwright/playwright?smo=true
    implementation("com.microsoft.playwright:playwright:1.55.0")
}

allure {
    version = "2.20.1"
    downloadLinkFormat = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.20.1/allure-commandline-2.20.1.zip"
}

// Usage: ./gradlew playwright --args="help"
tasks.register<JavaExec>("playwright") {
    classpath(sourceSets["test"].runtimeClasspath)
    mainClass.set("com.microsoft.playwright.CLI")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
application {
    mainClass.set("com.microsoft.playwright.CLI")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()

    // Configuration parameters to execute top-level classes in parallel but methods in same thread
    systemProperties["junit.jupiter.execution.parallel.enabled"] = true
    systemProperties["junit.jupiter.execution.parallel.config.strategy"]= "dynamic"
    systemProperties["junit.jupiter.execution.parallel.mode.default"] = "concurrent"
    systemProperties["junit.jupiter.execution.parallel.mode.classes.default"] = "concurrent"

    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.SHORT
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }

}
