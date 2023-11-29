FROM maven:3-amazoncorretto-17 AS build

COPY mvnw .
COPY pom.xml .
RUN mvn verify

ADD . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2023

COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]