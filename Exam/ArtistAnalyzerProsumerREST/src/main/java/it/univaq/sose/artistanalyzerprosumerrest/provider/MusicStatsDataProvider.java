package it.univaq.sose.artistanalyzerprosumerrest.provider;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;


@Component
public class MusicStatsDataProvider {

    @Value("${musicstats.uri}")
    private String musicStatsBaseURI;

    @Value("${streamingavailability.uri}")
    private String streamingAvailabilityBaseURI;

    private final WebClient webClient;

    // Best Practice: Build the WebClient once in the constructor
    public MusicStatsDataProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Song getSongById(Integer songId) {
        return webClient.get()
                // Use URI variables instead of string concatenation for safety and cleaner code
                .uri(musicStatsBaseURI + "/songs/{id}", songId)
                .retrieve()
                .bodyToMono(Song.class)
                // flatMap chains the next async call AFTER the first one succeeds
                .flatMap(song ->
                        webClient.get()
                                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-song-availability/{id}", songId)
                                .retrieve()
                                .bodyToFlux(StreamingService.class)
                                .collectList() // Converts Flux<StreamingService> to Mono<List<StreamingService>> asynchronously
                                .map(services -> {
                                    song.setStreamingServices(services);
                                    return song;
                                })
                )
                // .block() waits for the entire async chain to finish and returns the actual Song object
                .block();
    }

    public List<Song> getAllSongs(){
        return webClient.get()
                .uri(musicStatsBaseURI + "/songs")
                .retrieve()
                .bodyToFlux(Song.class)
                .flatMap(song ->
                        webClient.get()
                                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-song-availability/{id}", song.getId())
                                .retrieve()
                                .bodyToFlux(StreamingService.class)
                                .collectList()
                                .map(services -> {
                                    song.setStreamingServices(services);
                                    return song;
                                })
                )
                .collectList()
                .block();
    }

    public List<Song> getAllByArtist(String artist) {
        return webClient.get()
                .uri(musicStatsBaseURI + "/songs/by-artist/{artist}", artist)
                .retrieve()
                .bodyToFlux(Song.class)
                // flatMap handles the concurrent fetching of streaming services for EVERY song in the flux
                .flatMap(song ->
                        webClient.get()
                                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-song-availability/{id}", song.getId())
                                .retrieve()
                                .bodyToFlux(StreamingService.class)
                                .collectList()
                                .map(services -> {
                                    song.setStreamingServices(services);
                                    return song;
                                })
                )
                // Collect all the fully populated Song objects into a List
                .collectList()
                // Block until all songs and their respective services are fetched
                .block();
    }

    public Artist getArtistById(int id) {
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists/{id}", id)
                .retrieve()
                .bodyToMono(Artist.class)
                .block();

    }


    public Artist getArtistByName(String name){
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists/by-name/{name}", name)
                .retrieve()
                .bodyToMono(Artist.class)
                .block();
    }


    public List<Artist> getAllArtists(){
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists")
                .retrieve()
                .bodyToFlux(Artist.class)
                .collectList()
                .block();
    }

}
