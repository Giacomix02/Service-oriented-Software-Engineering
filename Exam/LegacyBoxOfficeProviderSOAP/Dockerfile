FROM eclipse-temurin:17

WORKDIR /LegacyBoxOfficeProviderSOAP
COPY  . .

RUN mvn clean install
CMD mvn spring-boot:run