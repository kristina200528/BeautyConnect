FROM eclipse-temurin:17.0.14_7-jre-ubi9-minimal

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
