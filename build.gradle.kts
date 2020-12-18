plugins {
    `java`
    `groovy`
    id("antlr")
}

group = "aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets.create("generated") {
    java.srcDir("src/main/generated")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")

    antlr("org.antlr:antlr4:4.7.2")
    "generatedImplementation"("org.antlr:antlr4-runtime:4.7.2")
    implementation(sourceSets.getByName("generated").output)
    implementation("org.codehaus.groovy:groovy:3.0.7")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("io.vavr:vavr:0.10.3")
    implementation("commons-codec:commons-codec:1.15")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments.addAll(listOf("-package", "aoc.parser"))
    outputDirectory = file("src/main/generated/aoc/parser")
}

tasks.compileJava {
    dependsOn(":generateGrammarSource")
//    source(sourceSets.getByName("generated").java, sourceSets.main.java)
}

tasks.clean {
    delete("src/main/generated")
}

