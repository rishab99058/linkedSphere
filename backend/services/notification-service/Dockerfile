FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /opt/app

# Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Shared module POM
COPY modules/common/pom.xml modules/common/
COPY modules/logging-core/pom.xml modules/logging-core/
COPY modules/testing-core/pom.xml modules/testing-core/
COPY modules/security-core/pom.xml modules/security-core/
COPY modules/starter-openai/pom.xml modules/starter-openai/

# All service POMs
COPY services/auth-service/pom.xml services/auth-service/
COPY services/discovery-server/pom.xml services/discovery-server/
COPY services/gateway-service/pom.xml services/gateway-service/
COPY services/notification-service/pom.xml services/notification-service/
COPY services/user-service/pom.xml services/user-service/

# Download dependencies
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline

# Required source code
COPY modules/common/src modules/common/src/
COPY services/notification-service/src services/notification-service/src/

# Build notification-service + required Maven modules
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean package \
    -pl services/notification-service \
    -am \
    -DskipTests

# Runtime
FROM eclipse-temurin:21-jre

WORKDIR /opt/app

COPY --from=builder \
    /opt/app/services/notification-service/target/*.jar \
    app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]