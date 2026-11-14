plugins {
    id("java-library")
}

group = "net.meme20200"
version = "1.5"

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/")
    }
    maven("https://repo.faststats.dev/releases")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    disableAutoTargetJvm()
}
dependencies {
    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.it.unimi.dsi.fastutil)
    implementation(libs.dev.faststats.metrics.bukkit)
    implementation(project(":shared"))
}