import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate

val postgresVersion = "42.7.2"
val telegramBotVersion = "7.10.0"

plugins {
    id("nu.studer.jooq") version("9.0")
    id("org.flywaydb.flyway") version("9.22.3")
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

group = "ru.template.telegram.bot.kotlin"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // gradle 8.8
    }
}

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

extra["springCloudVersion"] = "2023.0.3"

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
                        withPojos(true)
                        withDeprecated(false)
                        withRelations(true)
                        withRecords(true)
                        withPojosEqualsAndHashCode(true)
                        withFluentSetters(true)
                        withJavaTimeTypes(true)
                        withKotlinSetterJvmNameAnnotationsOnIsPrefix(true)
                        withPojosAsKotlinDataClasses(true)
                        withKotlinNotNullPojoAttributes(true)
                        withKotlinNotNullInterfaceAttributes(true)
                        withKotlinNotNullRecordAttributes(true)
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
