FROM eclipse-temurin:26-jdk

WORKDIR /StreamingAvailabilityProviderREST
COPY  . .


RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix mvnw
RUN chmod +x mvnw
RUN ./mvnw clean install -DskipTests
CMD ["./mvnw", "spring-boot:run"]