package it.univaq.sose.artistanalyzerprosumerrest.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;


@Component
public class StreamingServiceDataProvider {

    @Value("${musicstats.uri}")
    private String musicStatsBaseURI;

    @Value("${streamingavailability.uri}")
    private String streamingAvailabilityBaseURI;

    private final WebClient webClient;

    // Best Practice: Build the WebClient once in the constructor
    public StreamingServiceDataProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public List<StreamingService> getAllStreamingServices() {
        return webClient.get()
                .uri(streamingAvailabilityBaseURI + "/getAllStreamingServicies")
                .retrieve()
                .bodyToFlux(StreamingService.class)
                .collectList() // Converts Flux<StreamingService> to Mono<List<StreamingService>> asynchronously
                .block(); // Waits for the async call to complete and returns the List<StreamingService>
    }
}
