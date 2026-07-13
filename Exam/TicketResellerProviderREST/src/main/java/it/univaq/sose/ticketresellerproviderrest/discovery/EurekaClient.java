package it.univaq.sose.ticketresellerproviderrest.discovery;

import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EurekaClient {

    private static final String APP_NAME = "TICKET-RESELLER";
    private static final String EUREKA_URL = System.getenv().getOrDefault("EUREKA_SERVER", "http://localhost:8761/eureka/");
    private static final String PORT = System.getenv().getOrDefault("INTERNAL_PORT", "8080");
    private static final String HOSTNAME = System.getenv().getOrDefault("EUREKA_HOSTNAME",
            System.getenv().getOrDefault("HOSTNAME", "localhost"));

    private static final String INSTANCE_ID = HOSTNAME + ":" + APP_NAME.toLowerCase() + DigestUtils.sha256Hex(String.valueOf(Math.random())) + ":" + PORT;

    private final HttpClient httpClient;
    private ScheduledExecutorService scheduler;

    public EurekaClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // Registers the microservice instance with the Eureka Server
    public void register() {
        try {
            String jsonBody = """
                {
                  "instance": {
                    "instanceId": "%s",
                    "hostName": "%s",
                    "app": "%s",
                    "ipAddr": "%s",
                    "status": "UP",
                    "port": {"$": %s, "@enabled": "true"},
                    "dataCenterInfo": {
                      "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                      "name": "MyOwn"
                    }
                  }
                }
                """.formatted(INSTANCE_ID, HOSTNAME, APP_NAME, HOSTNAME, PORT);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(EUREKA_URL + "apps/" + APP_NAME))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Eureka Registration Status: " + response.statusCode());
        } catch (Exception e) {
            System.err.println("Error registering with Eureka: " + e.getMessage());
        }
    }

    // Sends renewals (heartbeats) in the background to Eureka
    public void startHeartbeat() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(EUREKA_URL + "apps/" + APP_NAME + "/" + INSTANCE_ID))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

                if (response.statusCode() == 404) {
                    System.out.println("Instance not found in Eureka (404), re-registering.");
                    register();
                } else {
                    System.out.println("Heartbeat sent to Eureka for " + INSTANCE_ID + " (status " + response.statusCode() + ")");
                }
            } catch (Exception e) {
                System.err.println("Error sending heartbeat: " + e.getMessage());
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    // Remove the instance from the Eureka registry
    public void deregister() {
        try {
            if (scheduler != null) {
                scheduler.shutdownNow();
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(EUREKA_URL + "apps/" + APP_NAME + "/" + INSTANCE_ID))
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            System.err.println("Error al desregistrar de Eureka: " + e.getMessage());
        }
    }
}