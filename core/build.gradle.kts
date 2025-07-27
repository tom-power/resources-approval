plugins {
    kotlin("jvm") version "2.0.21"

    idea
    `java-library`
}

version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test"))
    implementation("com.approvaltests:approvaltests:25.0.23")
}


base {
    archivesName = "resources-approval-core"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
