rootProject.name = "dgs.issue.repro"

pluginManagement {
    // https://docs.gradle.org/current/userguide/plugins.html#sec:plugin_version_management
    val springBootVersion: String by settings
    val kotlinVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    }
}