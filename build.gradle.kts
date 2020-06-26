import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val logback_version: String by project

plugins {
    java
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

this.group = "com.kenvix"
this.version = "2.0"
val archivesBaseName = "kenvix-utils"

repositories {
    mavenLocal()
    jcenter()
    google()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly("org.slf4j:slf4j-api:1.7.30")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.7")

    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")

    compileOnly("io.github.cdimascio:java-dotenv:5.1.4")
}

tasks {
    withType<AbstractCompile> {
        sourceCompatibility = "1.8"
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveBaseName.set(archivesBaseName)
        archiveVersion.set(this@Build_gradle.version.toString())

        destinationDirectory.set(file("${buildDir}/output"))
        isZip64 = true
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = sourceCompatibility
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    withType<JavaCompile> {
        options.encoding = "utf-8"
    }
}