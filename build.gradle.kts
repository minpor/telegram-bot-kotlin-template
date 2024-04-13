import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate

val postgresVersion = "42.7.0"
val telegramBotVersion = "7.2.1"

plugins {
    id("nu.studer.jooq") version("8.2.1")
    id("org.flywaydb.flyway") version("9.22.3")
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

group = "ru.template.telegram.bot.kotlin"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.clean {
    delete("src/main/java")
}

extra["springCloudVersion"] = "2023.0.1"

val flywayMigration = configurations.create("flywayMigration")


flyway {
    validateOnMigrate = false
    configurations = arrayOf("flywayMigration")
    url = "jdbc:postgresql://localhost:5432/kotlin_template"
    user = "postgres"
    password = "postgres"
}

dependencies {
    flywayMigration("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.telegram:telegrambots-springboot-longpolling-starter:$telegramBotVersion")
    implementation("org.telegram:telegrambots-extensions:$telegramBotVersion")
    implementation("org.telegram:telegrambots-client:$telegramBotVersion")


    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = flyway.url
                    user = flyway.user
                    password = flyway.password
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = false
                        isFluentSetters = false
                        isJavaBeansGettersAndSetters = false
                        isSerializablePojos = true
                        isVarargSetters = false
                        isPojos = true
                        isUdts = false
                        isRoutines = false
                        isIndexes = false
                        isRelations = true
                        isPojosEqualsAndHashCode = true
                    }
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway_schema_history|spatial_ref_sys|st_.*|_st.*"
                    }
                    target.apply {
                        packageName = "ru.template.telegram.bot.kotlin.template.domain"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }

    tasks.named<JooqGenerate>("generateJooq").configure {
        inputs.files(fileTree("src/main/resources/db/migration"))
            .withPropertyName("migrations")
            .withPathSensitivity(PathSensitivity.RELATIVE)
        allInputsDeclared.set(true)
        outputs.upToDateWhen { false }
    }
}
