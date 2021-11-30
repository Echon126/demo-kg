FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG docker-demo
COPY demo-0.0.1-SNAPSHOT.jar demo-app.jar
ENTRYPOINT ["java","-jar","/app.jar"]