FROM eclipse-temurin:17

WORKDIR /TicketSearcherProsumerREST
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run