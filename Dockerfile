FROM eclipse-temurin:17-jdk-alpine
RUN addgroup -S demo && adduser -S demo -G demo
USER demo
ARG JAR_FILE=/target/*.jar
WORKDIR /library-rest-service
COPY ${JAR_FILE} application.jar
COPY ./src/main/resources/data ./src/main/resources/data
ENTRYPOINT [ "java", "-jar", "application.jar" ]