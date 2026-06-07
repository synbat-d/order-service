FROM eclipse-temurin:17
RUN useradd spring
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} order-service.jar
COPY build/agent/opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar
RUN chown -R spring /workspace
USER spring
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java", "-jar", "order-service.jar"]