FROM maven:3.9.11-eclipse-temurin-21 AS builder

WORKDIR /opt/app

# Root Maven files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Module POMs
COPY modules/common/pom.xml modules/common/
COPY modules/logging-core/pom.xml modules/logging-core/
COPY modules/testing-core/pom.xml modules/testing-core/
COPY modules/security-core/pom.xml modules/security-core/
COPY modules/starter-openai/pom.xml modules/starter-openai/

# Service POMs
COPY services/auth-service/pom.xml services/auth-service/
COPY services/gateway-service/pom.xml services/gateway-service/
COPY services/user-service/pom.xml services/user-service/
COPY services/discovery-server/pom.xml services/discovery-server/
COPY services/notification-service/pom.xml services/notification-service/
COPY services/post-service/pom.xml services/post-service/

# Download dependencies
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline

# Copy required source
COPY modules/common/src modules/common/src/
COPY services/post-service/src services/post-service/src/

# Build user-service and required modules
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package \
    -pl services/post-service \
    -am \
    -DskipTests

# Runtime
FROM eclipse-temurin:21-jre

WORKDIR /opt/app

COPY --from=builder \
    /opt/app/services/post-service/target/post-service-0.0.1-SNAPSHOT.jar \
    app.jar

EXPOSE 8009

ENTRYPOINT ["java", "-jar", "app.jar"]