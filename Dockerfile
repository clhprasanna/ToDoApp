# Docker Multi stage build

# Stage 1: Build the application
FROM openjdk:17-slim as build

WORKDIR /workspace/app

COPY gradlew /workspace/app/
COPY gradle /workspace/app/gradle/

RUN chmod +x ./gradlew

COPY build.gradle.kts settings.gradle.kts /workspace/app/

RUN ./gradlew --no-daemon dependencies

COPY src /workspace/app/src/

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Setup the runtime environment
FROM openjdk:17-slim

COPY --from=build /workspace/app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
