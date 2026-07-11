package it.univaq.sose.musicstatsproviderprosumerrest.service;

import java.util.List;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;

public interface ArtistService {

    List<Artist> findAll();

    Artist findById(Integer id);

    List<Artist> findByName(String name);
    
}
