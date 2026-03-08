FROM eclipse-temurin:17
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} order-service.jar
ENTRYPOINT ["java", "-jar", "order-service.jar"]