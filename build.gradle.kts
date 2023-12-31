/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.2.1/samples
 * This project uses @Incubating APIs which are subject to change.
 */
val scalaVersion: String by project
val scalaSpecificVersion: String by project
val dataCatererVersion: String by project


plugins {
    scala
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    compileOnly("org.scala-lang:scala-library:$scalaSpecificVersion")

    compileOnly("io.github.data-catering:data-caterer-api:$dataCatererVersion")
}

