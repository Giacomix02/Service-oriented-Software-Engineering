FROM eclipse-temurin:17-jdk

WORKDIR /MusicStatsProviderProsumerREST
COPY target/MusicStatsProviderProsumerREST-0.0.1-SNAPSHOT.jar .

# RUN apt-get update && apt-get install -y dos2unix
# RUN dos2unix mvnw
# RUN chmod +x mvnw
# RUN ./mvnw clean install -DskipTests
CMD ["java","-jar", "MusicStatsProviderProsumerREST-0.0.1-SNAPSHOT.jar"]