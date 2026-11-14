plugins {
    id("java")
}

group = "net.meme20200"
version = "1.5"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    disableAutoTargetJvm()
}

repositories {
    mavenCentral()
}