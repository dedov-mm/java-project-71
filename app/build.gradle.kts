plugins {
    application
    jacoco
    id("checkstyle")
}

group = "hexlet.code"

version = "1.0-SNAPSHOT"

application { mainClass.set("hexlet.code.App") }

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.7" // Убедитесь, что используете актуальную версию
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)  // Генерируем XML-отчет
        html.required.set(true)  // Генерируем HTML-отчет (опционально)
    }
}