FROM eclipse-temurin:17

WORKDIR /ArtistAnalyzerProsumerREST
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run