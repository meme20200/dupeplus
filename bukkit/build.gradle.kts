import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.internal.extensions.core.serviceOf

plugins {
    id("java-library")
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
}

val testMinecraftServerVersion = "26.2"
val testMinecraftServerJavaVersion = "25"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    disableAutoTargetJvm()
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
    maven {
        url = uri("https://repo.thenextlvl.net/releases")
    }
    maven {
        url = uri("https://libraries.minecraft.net/")
    }
    maven {
        url = uri("https://mvn.wesjd.net/")
    }
}

dependencies {
    implementation(libs.dev.triumphteam.triumph.gui)
    implementation(libs.net.kyori.adventure.platform.bukkit)
    implementation(libs.net.kyori.adventure.text.minimessage)
    implementation(libs.net.kyori.adventure.text.serializer.plain)
    implementation(libs.org.bstats.bstats.bukkit)
    implementation(libs.org.json.json)
    implementation(libs.de.tr7zw.item.nbt.api)
    implementation(libs.org.incendo.cloud.paper)
    implementation(libs.me.lucko.commodore)
    implementation(libs.net.wesjd.anvilgui)
    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.com.github.milkbowl.vaultapi)
    compileOnly(libs.me.clip.placeholderapi)
    compileOnly(libs.com.mojang.authlib)
    implementation(project(":modern"))
    implementation(project(":shared"))
}

group = "net.meme20200"
version = "1.5"
description = "DupePlus"

tasks.processResources {
    val fastutilVersion = libs.versions.it.unimi.dsi.fastutil.get()
    val projectVersion = project.version

    inputs.property("fastutilversion", fastutilVersion)
    inputs.property("version", projectVersion)

    filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
        filter(
            ReplaceTokens::class,
            "tokens" to mapOf("version" to projectVersion, "fastutilversion" to fastutilVersion)
        )
    }
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
tasks {
    shadowJar {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        filesMatching("META-INF/*.kotlin_module") {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
        dependencies {
            exclude(dependency("com.mojang:brigadier"))
        }
        minimize {
            exclude(project(":modern"))
            exclude(project(":shared"))
            exclude(dependency("org.incendo:.*:.*"))
            exclude(dependency("me.lucko:.*:.*"))
            exclude(dependency("net.wesjd:anvilgui:.*"))
        }

        relocate("org.bstats", "net.meme20200.libs.bstats")
        relocate("dev.triumphteam.gui", "net.meme20200.libs.gui")
        relocate("de.tr7zw.changeme.nbtapi", "net.meme20200.libs.nbtapi")
        relocate("me.lucko.commodore", "net.meme20200.libs.commodore")
        relocate("org.incendo.cloud", "net.meme20200.libs.cloud")
        relocate("net.kyori", "net.meme20200.libs.adventure")

        archiveFileName = "${rootProject.name}-${rootProject.version}.jar"
    }
    runServer {
        minecraftVersion(testMinecraftServerVersion)
        val toolchainService = project.serviceOf<JavaToolchainService>()
        javaLauncher.set(toolchainService.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(testMinecraftServerJavaVersion))
        })
        jvmArgs("-Djava.awt.headless=true")
        runDirectory.set(rootProject.layout.projectDirectory.dir("run"))
    }
}