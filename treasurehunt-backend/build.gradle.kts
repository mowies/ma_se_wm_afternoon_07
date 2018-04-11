group = "com.geoschnitzel"
version = "1.0-SNAPSHOT"

buildscript {
    val springBootVersion by extra { "2.0.1.RELEASE" }

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

apply {
    plugin("io.spring.dependency-management")
    plugin("org.springframework.boot")
}

plugins {
    java
    application
}

application {
    mainClassName = "com.geoschnitzel.treasurehunt.backend.HelloWorld"
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-web")
    runtime("org.hsqldb:hsqldb")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
