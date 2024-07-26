plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.16.0"
}

group = "ltd.guimc.lgzbot.wynn"
version = "0.1.0"

dependencies {
    val overflow_version = "2.16.0-db61867-SNAPSHOT"
    implementation(kotlin("stdlib"))

    compileOnly("top.mrxiaom:overflow-core-api:$overflow_version")
    compileOnly("top.mrxiaom:overflow-core:$overflow_version")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

repositories {
    if (System.getenv("CI")?.toBoolean() != true) {
        maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    }
    mavenCentral()
}
