FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /opt/app

# Copy root maven files
COPY pom.xml .
COPY mvnw .  
COPY .mvn .mvn

# Copy Module POMs
COPY modules/common/pom.xml modules/common/
COPY modules/logging-core/pom.xml modules/logging-core/
COPY modules/testing-core/pom.xml modules/testing-core/
COPY modules/security-core/pom.xml modules/security-core/
COPY modules/starter-openai/pom.xml modules/starter-openai/

# Copy Service POMs
COPY services/auth-service/pom.xml services/auth-service/
COPY services/gateway-service/pom.xml services/gateway-service/
COPY services/user-service/pom.xml services/user-service/
COPY services/discovery-server/pom.xml services/discovery-server/


# Download dependencies offline
RUN ./mvnw dependency:go-offline

# Copy source code for common and user-service
COPY modules/common/src modules/common/src/
COPY services/user-service/src services/user-service/src/
COPY services/discovery-server/src services/discovery-server/src/

# Package user-service
RUN ./mvnw clean package \
    -pl services/user-service \
    -am \
    -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre

WORKDIR /opt/app

COPY --from=builder /opt/app/services/user-service/target/user-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7999

ENTRYPOINT ["java", "-jar", "app.jar"]
