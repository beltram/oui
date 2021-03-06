import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.platform.console.options.Details
import org.junit.platform.console.options.Details.VERBOSE
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import org.junit.platform.gradle.plugin.JUnitPlatformPlugin

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.0.RC1")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
}

plugins {
    val kotlinVersion = "1.2.21"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
}

version = "1.0.0-SNAPSHOT"

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

configure<JUnitPlatformExtension> {
    details = VERBOSE
}


repositories {
    jcenter()
    mavenCentral()
    maven("http://repo.spring.io/milestone")
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-webflux") {
        exclude(module = "spring-boot-starter-tomcat")
    }
    compile("org.springframework.boot:spring-boot-starter-reactor-netty")
    compile("io.projectreactor.ipc:reactor-netty")
    compile("org.springframework:spring-context")
    compile("io.netty:netty-buffer")
    compile("com.fasterxml.jackson.core:jackson-databind")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testCompile("org.junit.jupiter:junit-jupiter-api")
    testCompile("io.projectreactor:reactor-test")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testCompile("org.springframework.security:spring-security-test")
}