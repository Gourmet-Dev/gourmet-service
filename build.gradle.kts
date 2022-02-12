plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
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

    group = "com.gourmet"
    version = "0.0.1-SNAPSHOT"

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
}
