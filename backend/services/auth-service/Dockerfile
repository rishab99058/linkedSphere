FROM maven:3.9.11-eclipse-temurin-21 AS builder

# Above consists of three part 
# FROM => I want to create a image from existing image 
# Image which have => Ubentu/ linux, java 21 and maven 3.9.11
# As Builder => I am giving a name to this image which is BUILDER

WORKDIR /opt/app
# WORKDIR => we are creating a work directory 
# /opt/app => opt stands for otional applicatio software (third party software) and our project is also third part software

#COPY . .

## It will copy inside OPT/APP

## /opt/app
## backend/
## services/
## common/

COPY pom.xml .
COPY mvnw .  
# We are copying the maven wrapper script because it is required to build the project 
COPY .mvn .mvn
# We are copying the maven wrapper jar and maven wrapper shell script

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


RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline
# Will download all the dependencies of all three from the internet 
COPY modules/common/src modules/common/src/
COPY services/auth-service/src services/auth-service/src/

RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package \
    -pl services/auth-service \
    -am \
    -DskipTests

# -am will build all the dependencies recursively
# in topological order

# Runtime Stage
FROM eclipse-temurin:21-jre

WORKDIR /opt/app

COPY --from=builder /opt/app/services/auth-service/target/auth-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build --no-cache \
#   -f services/auth-service/Dockerfile \
#   -t rishab99058/linksphere/auth-service:v1.0.0 \
#   .

# docker tag rishab99058/linksphere/auth-service:v1.0.0 \
#  rishab99058/linksphere-auth-service:v1.0.0

# docker push rishab99058/linksphere-auth-service:v1.0.1
