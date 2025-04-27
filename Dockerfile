# 1. Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests



# 2. Run stage


# 1. Use official Java runtime
FROM openjdk:21-jdk-slim

# 2. Set the working directory
WORKDIR /app

# 3. Copy the JAR file to the container
COPY target/*.jar app.jar

# 4. Expose the port your app runs on
EXPOSE 8080

# 5. Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
