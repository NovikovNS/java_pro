plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("com.google.code.gson:gson:2.10.1")
    api("ch.qos.logback:logback-core:1.3.5")
    api("ch.qos.logback:logback-classic:1.3.5")
    api("org.slf4j:slf4j-api:2.0.4")
}

group = "ru.flamexander.http.server"
version = "1.0-SNAPSHOT"
description = "fx-http-server"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
