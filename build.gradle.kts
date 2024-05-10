plugins {
	java
	id("org.springframework.boot") version "3.3.0-M3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.todo.app"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.xerial:sqlite-jdbc:3.44.1.0")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework:spring-test")
	testImplementation("org.mockito:mockito-core:3.6.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
