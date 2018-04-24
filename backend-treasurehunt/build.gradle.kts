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
    id("net.ltgt.apt").version("0.10")
}

application {
    mainClassName = "com.geoschnitzel.treasurehunt.backend.TreasureHuntApplication"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.16.20")
    apt("org.projectlombok:lombok:1.16.20")

    compile(project(":shared-treasurehunt"))
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-web")
    runtime("org.hsqldb:hsqldb")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.hamcrest", "hamcrest-all", "1.3")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
