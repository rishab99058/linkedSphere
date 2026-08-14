FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /opt/app
COPY pom.xml .
COPY mvnw . 
COPY .mvn .mvn

COPY modules/common/pom.xml modules/common/
COPY modules/logging-core/pom.xml modules/logging-core/
COPY modules/testing-core/pom.xml modules/testing-core/
COPY modules/security-core/pom.xml modules/security-core/
COPY modules/starter-openai/pom.xml modules/starter-openai/

COPY services/auth-service/pom.xml services/auth-service/
COPY services/gateway-service/pom.xml services/gateway-service/
COPY services/user-service/pom.xml services/user-service/
COPY services/discovery-server/pom.xml services/discovery-server/

RUN ./mvnw dependency:go-offline

COPY services/gateway-service/src services/gateway-service/src/

RUN ./mvnw clean package \
    -pl services/gateway-service \
    -am \
    -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /opt/app

COPY --from=builder \
    /opt/app/services/gateway-service/target/*.jar \
    gateway-service.jar



ENTRYPOINT ["java", "-jar", "gateway-service.jar"]
