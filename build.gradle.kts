import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "fi.vamk"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

flyway {
  url = "jdbc:postgresql://localhost:5432/vabanque"
  user = "postgres"
  password = "zaq1@WSX"
}

val developmentOnly by configurations.creating
configurations {
  runtimeClasspath {
    extendsFrom(developmentOnly)
  }
}

repositories {
  mavenCentral()
  jcenter()
}

plugins {
  id("org.springframework.boot") version "2.1.8.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  id("org.jetbrains.kotlin.jvm") version "1.2.71"
  id("org.jetbrains.kotlin.plugin.spring") version "1.2.71"
  id("org.jetbrains.kotlin.plugin.jpa") version "1.2.71"
  id("org.flywaydb.flyway") version "6.0.4"
  id("org.jlleitschuh.gradle.ktlint") version "9.0.0"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-websocket")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("io.jsonwebtoken:jjwt:0.+")
  implementation("io.springfox:springfox-swagger-ui:2.9.2")
  implementation("io.springfox:springfox-swagger2:2.9.2")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  compile("org.postgresql:postgresql")
  testImplementation("org.springframework:spring-test")
  testImplementation("org.springframework.boot:spring-boot-test")
  testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
  testImplementation("org.junit.jupiter:junit-jupiter:5.+")
  testImplementation("org.assertj:assertj-core:3.+")
  testImplementation("io.mockk:mockk:1.+")
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}
