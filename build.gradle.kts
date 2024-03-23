import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.20"
    application
}

group = "cz.trapincho"
version = "1.8.5"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://maven.scijava.org/content/groups/public/")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.0")

    implementation("net.dv8tion:JDA:5.0.0-alpha.12")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.github.microutils:kotlin-logging:2.1.23")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// I have no idea how or why this works
// courtesy of https://stackoverflow.com/a/70832720
tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.WARN
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "cz.trapincho.wynntheorybot.MainKt"
    }
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
