import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.20"
    application
}

group = "cz.trapincho"
version = "1.7.0"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://maven.scijava.org/content/groups/public/")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")

    implementation("net.dv8tion:JDA:4.3.0_336")
    implementation("com.jagrosh:jda-utilities:3.0.5")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")


    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
    implementation("ch.qos.logback:logback-core:1.3.0-alpha5")
    implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")

    implementation("io.github.microutils:kotlin-logging:2.0.11")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
