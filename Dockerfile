FROM gradle:7.6.0-jdk17 AS build

# Set the working directory for Gradle
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle ./

# Copy the source code into the working directory
COPY src ./src

# Build the application (this also pulls dependencies)
RUN gradle build --no-daemon -x test

FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
