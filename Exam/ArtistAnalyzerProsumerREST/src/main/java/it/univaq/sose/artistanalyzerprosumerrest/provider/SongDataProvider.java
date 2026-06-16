package it.univaq.sose.artistanalyzerprosumerrest.provider;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class SongDataProvider {

    @Value("${musicstats.uri}") //TODO if URI doesn't work migrate type to String, it will work anyway
    String musicStatsBaseURI;
  
    @Value("${streamingavailability.uri}")
    String streamingAvailabilityBaseURI;

    private final WebClient.Builder webClientBuilder = WebClient.builder();

    public Future<Song> get(Song song) {
        String uriMS = musicStatsBaseURI.concat(("/song/"+ song.getId()));
        Mono<Song> songMono = webClientBuilder.build()
                .get()
                .uri(uriMS)
                .retrieve()
                .bodyToMono(Song.class);
        String uriSA = streamingAvailabilityBaseURI.concat("/streamingAvailability/getSongAvailability/"+ song.getId());
        Flux<StreamingService> streamingServices = webClientBuilder.build()
                .get()
                .uri(uriSA)
                .retrieve()
                .bodyToFlux(StreamingService.class);

        
        return songMono.map( s -> {
            s.setStreamingServices(streamingServices.toStream().toList()); 
            return s;
        } ).toFuture();       

    }


    public Stream<Song> getAll(String artist){
        String uriMS = musicStatsBaseURI.concat(("/song/byArtist/"+ artist));

        Flux<Song> songFlux = webClientBuilder.build()
                .get()
                .uri(uriMS)
                .retrieve()
                .bodyToFlux(Song.class);

        songFlux.subscribe((Song s) -> {
            String uriSA = streamingAvailabilityBaseURI.concat("/streamingAvailability/getSongAvailability/"+ s.getId());
            Flux<StreamingService> streamingServices = webClientBuilder.build()
                .get()
                .uri(uriSA)
                .retrieve()
                .bodyToFlux(StreamingService.class);

            s.setStreamingServices(streamingServices.toStream().toList());
        });
        return songFlux.toStream();
                          
    }
        
}
