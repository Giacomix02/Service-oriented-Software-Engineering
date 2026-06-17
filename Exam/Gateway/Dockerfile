FROM maven:3.9.16-eclipse-temurin-26

WORKDIR /Gateway
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run