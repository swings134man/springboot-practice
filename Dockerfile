# Docker file

# jdk11 Image Start
FROM openjdk:11-jdk
# 인자 정리 - Jar
ARG JAR_FILE=build/libs/*.jar
# jar File Copy
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]