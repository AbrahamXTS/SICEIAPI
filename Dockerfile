FROM openjdk:17-jdk-slim

WORKDIR /kardex-service

COPY target/*.jar /kardex-service/kardex-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/kardex-service/kardex-service.jar"]
