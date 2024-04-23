FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar buysell.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","buysell.jar"]
