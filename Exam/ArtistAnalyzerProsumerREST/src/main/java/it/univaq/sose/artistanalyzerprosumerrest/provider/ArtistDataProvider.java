package it.univaq.sose.artistanalyzerprosumerrest.provider;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;

@Component
public class ArtistDataProvider {

    @Value("${musicstats.uri}") //TODO if URI doesn't work migrate type to String, it will work anyway
    String baseURI;

    private final WebClient.Builder webClientBuilder = WebClient.builder();

    public Future<Artist> getById(Artist artist) {
        String uri = baseURI + "/artists/"+artist.getId();
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Artist.class).toFuture();

    }


    public Future<Artist> getByName(Artist artist){
        String uri = baseURI + "/artists/name/"+artist.getName();
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Artist.class).toFuture();
    }
        
}
