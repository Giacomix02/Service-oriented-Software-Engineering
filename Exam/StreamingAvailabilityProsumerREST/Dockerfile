FROM eclipse-temurin:17

WORKDIR /StreamingAvailabilityProsumerREST
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run