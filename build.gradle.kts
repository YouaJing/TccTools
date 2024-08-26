import java.text.SimpleDateFormat

plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.goooler.shadow").version("8.1.7")
}

repositories {
    mavenLocal()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://repo.maven.apache.org/maven2/")
    mavenCentral()
    maven ("https://repo.dmulloy2.net/repository/public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
}


dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly ("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("com.github.YufiriaMazenta:CrypticLib:1.0.5")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
group = "tcc.youajing"
version = "4.5"
var mainClass = "${rootProject.group}.${rootProject.name.lowercase()}.TccTools"
var pluginVersion: String = version.toString() + "-" + SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis())
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    val props = HashMap<String, String>()
    props["version"] = pluginVersion
    props["main"] = mainClass
    props["name"] = rootProject.name
    processResources {
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        relocate("crypticlib", "${rootProject.group}.${rootProject.name.lowercase()}.crypticlib")
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }
    assemble {
        dependsOn(shadowJar)
    }
}
