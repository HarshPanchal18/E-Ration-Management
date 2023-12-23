plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.harsh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    runtimeOnly("mysql:mysql-connector-java:8.0.+")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}