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
    id("org.ajoberstar.grgit") version "5.0.0"
    id("java-test-fixtures")
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
    apply(plugin = "org.ajoberstar.grgit")
    apply(plugin = "java-test-fixtures")

    group = "com.gourmet"
    version = grgit.describe { longDescr = true }
        ?: "0.0.0-${grgit.log().size}-g${grgit.head().abbreviatedId}"

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("$rootDir/config/detekt.yml")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.data:spring-data-commons:2.6.2")
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.6.4")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
        testImplementation("io.kotest:kotest-assertions-core:4.6.0")
        testImplementation("io.mockk:mockk:1.11.0")
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

project(":common:gourmet-common-helper") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
    }
}

project(":common:gourmet-common-mocker") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
    }
}

project(":core:gourmet-place-core") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
        implementation(project(":common:gourmet-common-helper"))
        implementation(project(":common:gourmet-common-mocker"))
        testFixturesImplementation(project(":common:gourmet-common-type"))
        testFixturesImplementation(project(":common:gourmet-common-helper"))
        testFixturesImplementation(project(":common:gourmet-common-mocker"))
    }
}

project(":infra:gourmet-place-persistence") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
        implementation(project(":common:gourmet-common-helper"))
        implementation(project(":common:gourmet-common-mocker"))
        implementation(project(":core:gourmet-place-core"))
    }
}

project(":app:gourmet-place-api") {
    dependencies {
        implementation(project(":common:gourmet-common-type"))
        implementation(project(":common:gourmet-common-helper"))
        implementation(project(":common:gourmet-common-mocker"))
        implementation(project(":core:gourmet-place-core"))
        implementation(project(":infra:gourmet-place-persistence"))
        testImplementation(testFixtures(project(":core:gourmet-place-core")))
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
