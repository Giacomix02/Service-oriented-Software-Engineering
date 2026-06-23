package it.univaq.sose.artistanalyzerprosumerrest.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.dto.ArtistDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.AvailabilityDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.SongDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.StreamingServiceDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.fromProviders.AvailabilityDTOProvider;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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

    public SongDTO getSongById(Integer songId) {
        return webClient.get()
                // Use URI variables instead of string concatenation for safety and cleaner code
                .uri(musicStatsBaseURI + "/songs/{id}", songId)
                .retrieve()
                .bodyToMono(SongDTO.class)
                // flatMap chains the next async call AFTER the first one succeeds
                .flatMap(song ->
                        webClient.get()
                                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-song-availability/{id}", songId)
                                .retrieve()
                                .bodyToFlux(StreamingServiceDTO.class)
                                .collectList() // Converts Flux<StreamingService> to Mono<List<StreamingService>> asynchronously
                                .map(services -> {
                                    song.setStreamingServices(services);
                                    return song;
                                })
                )
                // .block() waits for the entire async chain to finish and returns the actual Song object
                .block();
    }

    public List<SongDTO> getAllSongs(){
        return webClient.get()
                .uri(musicStatsBaseURI + "/songs")
                .retrieve()
                .bodyToFlux(SongDTO.class)
                .flatMap(song ->
                        webClient.get()
                                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-song-availability/{id}", song.getId())
                                .retrieve()
                                .bodyToFlux(StreamingServiceDTO.class)
                                .collectList()
                                .map(services -> {
                                    song.setStreamingServices(services);
                                    return song;
                                })
                )
                .collectList()
                .block();
    }

    public List<SongDTO> getAllByArtist(String artist) {

        Flux<SongDTO> songs = webClient.get()
                .uri(musicStatsBaseURI + "/songs/by-artist/{artist}", artist)
                .retrieve()
                .bodyToFlux(SongDTO.class);
        Flux<AvailabilityDTOProvider> availabilities = webClient.get()
                .uri(streamingAvailabilityBaseURI + "/streaming-availability/get-all-availabilities")
                .retrieve()
                .bodyToFlux(AvailabilityDTOProvider.class);

        Mono<List<SongDTO>> songsMono = songs.collectList();
        Mono<List<AvailabilityDTOProvider>> availabilitiesMono = availabilities.collectList();

        return Mono.zip(songsMono, availabilitiesMono).map(t -> {
            List<SongDTO> s = t.getT1();
            List<AvailabilityDTOProvider> av = t.getT2();

            return s.stream().map(son -> {
                AvailabilityDTOProvider avForSong = av.stream().filter(a -> a.getSong().getId() == son.getId()).findFirst().get();
                son.setStreamingServices(avForSong.getStreamingServices());
                
                return son;
            }).toList();

        }).block();
        // songs.collectList().block();
        // availabilities.collectList().block();
        // return songs.flatMap(song -> {
        //         Mono<AvailabilityDTOProvider> availabilitiesForSong = availabilities.filter(t -> t.getSong().getId() == song.getId()).single();
        //         List<StreamingServiceDTO> streamingServicesForSong = availabilitiesForSong.
        //         song.setStreamingServices(streamingServicesForSong);
        //         return Flux.just(song);
        //     }
        // )
        // // Collect all the fully populated Song objects into a List
        // .collectList()
        // // Block until all songs and their respective services are fetched
        // .block();
    }

    public ArtistDTO getArtistById(int id) {
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists/{id}", id)
                .retrieve()
                .bodyToMono(ArtistDTO.class)
                .block();

    }


    public ArtistDTO getArtistByName(String name){
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists/by-name/{name}", name)
                .retrieve()
                .bodyToMono(ArtistDTO.class)
                .block();
    }


    public List<ArtistDTO> getAllArtists(){
        return webClient
                .get()
                .uri(musicStatsBaseURI + "/artists")
                .retrieve()
                .bodyToFlux(ArtistDTO.class)
                .collectList()
                .block();
    }

}
