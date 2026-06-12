FROM eclipse-temurin:17

WORKDIR /TicketStatsProviderProsumerREST
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run