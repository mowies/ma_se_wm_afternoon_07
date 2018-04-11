group = "com.geoschnitzel"
version = "1.0-SNAPSHOT"

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
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
