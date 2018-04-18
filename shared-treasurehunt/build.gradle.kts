group = "com.geoschnitzel"
version = "1.0-SNAPSHOT"

apply {
    plugin("maven")
}

plugins {
    java
    kotlin("jvm").version("1.2.31")
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib"))

    testCompile("junit", "junit", "4.12")
    testCompile("org.hamcrest", "hamcrest-all", "1.3")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
