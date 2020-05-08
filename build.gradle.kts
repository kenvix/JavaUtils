import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val logback_version: String by project

plugins {
    java
    kotlin("multiplatform") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

this.group = "com.kenvix"
this.version = "2.0"

repositories {
    mavenLocal()
    jcenter()
    google()
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        // 仅用于 JVM 的源码及其依赖的默认源集
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                compileOnly("org.slf4j:slf4j-api:1.7.30")
                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")

                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")

                compileOnly("io.github.cdimascio:java-dotenv:5.1.4")
            }
        }
        // 仅用于 JVM 的测试及其依赖
        jvm().compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
}

tasks {
    withType<AbstractCompile> {
        sourceCompatibility = "1.8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = sourceCompatibility
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    withType<JavaCompile> {
        options.encoding = "utf-8"
    }
}