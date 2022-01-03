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
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    antlr("org.antlr:antlr4:4.9.3")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("io.vavr:vavr:0.10.4")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("org.apache.commons:commons-math3:3.5")
    implementation("com.github.dpaukov:combinatoricslib3:3.3.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation("org.springframework:spring-core:5.3.13")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments.addAll(listOf("-package", "aoc.y2020.day18.parser", "-visitor"))
}

application {
    mainClass.set("me.vforchi.aoc.Main")
}


