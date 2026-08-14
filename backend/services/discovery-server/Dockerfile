FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /opt/app
COPY pom.xml .
COPY mvnw . 
COPY .mvn .mvn

# Copy all the depedecies
COPY modules/common/pom.xml modules/common/pom.xml
COPY modules/logging-core/pom.xml modules/logging-core/pom.xml
COPY modules/testing-core/pom.xml modules/testing-core/pom.xml
COPY modules/security-core/pom.xml modules/security-core/pom.xml
COPY modules/starter-openai/pom.xml modules/starter-openai/pom.xml

# Copy all the service depedecies
COPY services/auth-service/pom.xml services/auth-service/pom.xml
COPY services/discovery-server/pom.xml services/discovery-server/pom.xml
COPY services/gateway-service/pom.xml services/gateway-service/pom.xml
COPY services/user-service/pom.xml services/user-service/pom.xml

RUN ./mvnw dependency:go-offline

COPY services/discovery-server/src services/discovery-server/src 
RUN ./mvnw clean package \
    -pl services/discovery-server \
    -am \
    -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /opt/app

COPY --from=builder /opt/app/services/discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]


