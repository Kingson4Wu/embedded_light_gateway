plugins {
    id("java")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework:spring-web:6.0.4")
    implementation("org.springframework:spring-core:6.0.4")
    implementation("org.springframework:spring-webmvc:6.0.4")
    compileOnly("javax.servlet:servlet-api:2.5")



    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}