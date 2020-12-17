plugins {
    `java`
    `groovy`
}

group = "aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.codehaus.groovy:groovy:3.0.7")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("io.vavr:vavr:0.10.3")
    implementation("commons-codec:commons-codec:1.15")
}
