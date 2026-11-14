plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":bukkit"))
    implementation(project(":modern"))
    implementation(project(":shared"))
}

group = "net.meme20200"
version = "1.5"
description = "DupePlus"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.register("shadowJar") {
    dependsOn(":bukkit:shadowJar")
}

tasks.named("build") {
    dependsOn(":bukkit:build")
}

subprojects {
    layout.buildDirectory.set(
        rootProject.layout.buildDirectory.dir(project.name)
    )
}