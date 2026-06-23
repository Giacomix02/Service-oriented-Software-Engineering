FROM eclipse-temurin:17-jdk

WORKDIR /StreamingAvailabilityProviderREST
COPY target/StreamingAvailabilityProviderREST-0.0.1-SNAPSHOT.jar .

# RUN apt-get update && apt-get install -y dos2unix
# RUN dos2unix mvnw
# RUN chmod +x mvnw
# RUN ./mvnw clean install -DskipTests
CMD ["java","-jar", "StreamingAvailabilityProviderREST-0.0.1-SNAPSHOT.jar"]