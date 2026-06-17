FROM maven:3.9.16-eclipse-temurin-26

WORKDIR /Gateway
COPY  . .

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests
CMD ["./mvnw", "spring-boot:run"]