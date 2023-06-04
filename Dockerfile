FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

WORKDIR /home/app

COPY pom.xml .

COPY src/ src/

RUN mvn -f /home/app/pom.xml clean package -DskipTests -X

FROM openjdk:21-jdk-slim

COPY --from=build /home/app/target/github-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
