package it.univaq.sose.artistanalyzerprosumerrest.service;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;

public interface ArtistService {
    List<Artist> findAll();
    Artist findById(int id);
    Artist findByName(String name);
}
