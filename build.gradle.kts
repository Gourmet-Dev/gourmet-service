val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/detekt.sarif"))
}

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    group = "com.gourmet"
    version = "0.0.1-SNAPSHOT"

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("$rootDir/config/detekt.yml")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
        test {
            useJUnitPlatform()
        }
        detekt {
            reports {
                sarif.required.set(true)
            }
        }
        bootJar {
            enabled = false
        }
        jar {
            enabled = true
        }
    }

    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin>().configureEach {
        tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach detekt@{
            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(this@detekt.sarifReportFile)
            }
        }
    }
}

project(":core:gourmet-place-core") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
    }
}

project(":infra:gourmet-place-persistence") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
        implementation(project(":core:gourmet-place-core"))
    }
}

project(":app:gourmet-place-api") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
        implementation(project(":core:gourmet-place-core"))
        implementation(project(":infra:gourmet-place-persistence"))
    }

    tasks.bootJar {
        enabled = true
        destinationDirectory.set(rootProject.buildDir.resolve("service/place-api/"))
    }
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = false
    }
}
