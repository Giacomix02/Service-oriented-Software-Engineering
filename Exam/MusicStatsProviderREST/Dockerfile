FROM eclipse-temurin:17

WORKDIR /MusicStatsProviderREST
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run