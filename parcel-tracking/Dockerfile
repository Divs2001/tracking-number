FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Copy source code
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build the application
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Find and run the JAR file
CMD ["java", "-jar", "target/parcel-tracking-0.0.1-SNAPSHOT.jar"]