plugins {
    java
    antlr
    application
}

group = "aoc"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")

    antlr("org.antlr:antlr4:4.7.2")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("io.vavr:vavr:0.10.3")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("org.apache.commons:commons-collections4:4.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments.addAll(listOf("-package", "aoc.y2020.day18.parser", "-visitor"))
}

application {
    mainClass.set("me.vforchi.aoc.Main")
}


