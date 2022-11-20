FROM arm64v8/gradle:7.5.1 as build
WORKDIR /
COPY . .
RUN gradle fatJar

FROM eclipse-temurin:17-jre-jammy as run
COPY --from=build /app/build/libs/app-app.jar /
CMD ["java", "-jar", "/app-app.jar"]
