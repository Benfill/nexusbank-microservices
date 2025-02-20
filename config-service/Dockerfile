FROM openjdk:8-jdk-alpine
RUN apk add --no-cache curl
VOLUME /tmp
COPY target/*.jar config-service.jar
ENTRYPOINT ["java","-jar","/config-service.jar"]