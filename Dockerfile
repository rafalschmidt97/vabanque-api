FROM openjdk:11-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew assemble --no-daemon

FROM openjdk:11-jre-slim AS runtime
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 80
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dserver.port=80 -jar /app/app.jar"]
