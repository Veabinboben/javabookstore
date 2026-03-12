# build stage
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/
COPY src/ src/
RUN chmod +x ./mvnw
RUN ./mvnw -B package -DskipTests

# runtime stage
FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
